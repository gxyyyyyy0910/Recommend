<template>
  <div>
    <el-row style="height: 950px;">
      <el-tooltip effect="dark" placement="right"
                  v-for="item in movies"
                  :key="item.id"
      >
        <h1 slot="content" style="font-size: 24px;margin-bottom: 6px;">{{item.name}}</h1>
        <div slot="content">
          <rate
              :grade=item.rate
          >
          </rate>
        </div>
        <p slot="content">导演：{{item.director}}</p>
        <p slot="content">主演：{{item.actor}}</p>
        <p slot="content">标签：{{item.tag}}</p>
        <p slot="content">观影人数：{{item.popular}}</p>
        <div class="categories" slot="content">
          <span class="tag-group__title">类型：</span>
          <el-tag
              style="margin: 3px"
              :key="category.id"
              effect="dark"
              v-for="category in (item.genre?item.genre.split(',').map(el => el.trim()).filter(item => item.trim() != ''):null)"
          >
            {{category}}
          </el-tag>
        </div>
        <p slot="content" style="width: 300px" class="abstract">简介：{{item.summary}}</p>
        <el-card style="background-color:transparent;border:none;width: 135px;margin-bottom: 10px;height: 300px;float: left;margin-right: 25px" class="movie"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover">
            <img :src="item.cover" alt="海报" referrerPolicy="no-referrer">
          </div>
          <div class="title" style="padding-left: 10px;">
            <a href="">{{item.name}}</a>
            <span class="rate" style="font-size: 10px;font-weight:bold;color: #f9ca05;margin-left: 5px;margin-top: 2px">{{item.rate}}</span>
            <!--
            <img src="../../assets/ico/delete.png" style="width: 15px;height: 15px;padding-left: 5px;" class="el-icon-delete" @click="deleteMovie(item.id)" alt="delete"
            -->
          </div>
        </el-card>
      </el-tooltip>
    </el-row>
    <el-row>
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
import EditInformation from "@/components/admin/content/EditInformation";
import Rate from "@/components/common/Rate";
import moviesNav from "@/components/moviepage/MoviesNav";
import movieSet from "@/components/moviepage/MovieSet";
import myBus from '@/assets/myBus.js'

export default {
  name: 'MoviesView',
  components: {EditInformation,Rate,moviesNav,movieSet},
  data () {
    return {
      currentPage:1,
      pageSize:24, //21 1500
      movies: [],
      total:0,
      cid:0,
      popularOrRate:0
    }
  },
  mounted: function () {
    //this.loadMovies()
    //this.getData()
    
    myBus.$on('msg', res=>{
      const _this = this;
      _this.cid = res
    })
    myBus.$on('msg2', res=>{
      const _this = this;
      _this.popularOrRate = res
    })
    this.listByCategory()
  },
  methods: {
    // loadMovies () {
    //   const _this = this;
    //   this.$axios.get('http://localhost:9090/movieinfo/findAll').then(resp => {
        
    //     console.log(resp.status)
    //     console.log(resp.data)
    //     if (resp && resp.status === 200) {
    //       _this.movies = resp.data
    //     }
    //   })
    // },
    handleCurrentChange: function (currentPage) {
      const _this = this;
      this.currentPage = currentPage
      console.log(this.currentPage)
      console.log(this.cid)
      console.log(_this.cid === '0')
      /*if (_this.cid === '0' &&  _this.popularOrRate === '1'){
        this.getData()
      }
      else{*/
        this.listByCategory()
      //}
      // this.getData()
    },
    getData(){
      const _this = this;
      let self = this;
      let pageNum = this.currentPage;//data:"pageNum="+pageNum
      self.$axios.post('http://localhost:9090/movieinfo/findAll',"pageNum="+pageNum)//,{headers:{"Content-Type":"application/json"}})
          .then(resp=>{
            
            if (resp && resp.status === 200) {
              
              _this.movies = resp.data
              _this.total = resp.data.total
            }
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
    },
    listByCategory() {
      //this.categoryView = true
      const _this = this
      const cid = _this.cid
      const popularOrRate = _this.popularOrRate
      const url = 'http://localhost:9090/movieinfo/getMovieByGenre/' + cid + '/' + popularOrRate
      
      _this.$axios.post(url,"pageNum="+this.currentPage).then(resp=>{
        if (resp && resp.status === 200) {
          this.movies = resp.data.list
          this.total = resp.data.total
        }
      })
    },
  }
}
</script>

<style lang="scss" scoped>

  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 14px;
    display: flex;
    justify-content: center;
  }
  .abstract {
    display: block;
    line-height: 17px;
  }

  a {
    text-decoration: none;
    height: 50px;
  }

  a:link, a:visited, a:focus {
    color: #ffffff;
  }
  p {
    font-size: 15px;
    width: 320px;
  }

  ::v-deep li.number.active{
    background: transparent !important;
  }

  ::v-deep li.number {
    background: transparent !important;
  }

  ::v-deep li.el-icon.more {
    background: transparent !important;
  }
  ::v-deep button.btn-prev {
    background: transparent !important;
  }

  ::v-deep button.btn-next {
    background: transparent !important;
  }
  ::v-deep{
    .el-pager li:not(.disabled){
      color:rgb(147, 162, 207) !important;
    }
    .el-pager li:hover{
      color: #4a89c3 !important;
    }
    .el-pager li.active{
      color: rgb(255, 255, 255) !important; 
    }
  }
 
</style>

