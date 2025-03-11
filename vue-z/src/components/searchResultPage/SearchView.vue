<template>
  <el-container>
    <el-header style="margin-bottom: 30px">
      <p v-if="movies.length" style="font-size: 35px;color: #00bfff">搜索"{{keywords}}"的结果...</p>
      <p v-else style="font-size: 35px;color: rgb(200, 227, 255)">暂无结果</p>
    </el-header>
    <el-container
      v-for="item in movies.slice((currentPage-1)*pageSize,currentPage*pageSize)"
      :key="item.id"
      style="height: 300px"
    >
      <el-aside
          width="200px"
      >
        <img :src="item.cover" alt="cover" referrerPolicy="no-referrer"/>
      </el-aside>
      <el-main style="padding-top: 0px">
        <div class="movieInformation" >
          <p><span style="color: rgb(255, 255, 255);font-size: 25px;font-weight: 900;">{{item.name}}</span><a class="movie-rate" style="font-weight: bold;">评分：</a><span class="movie-rate-span">{{item.rate}}</span></p>
          <p><a style="font-weight: bold;color:#00bfff ;">导演：</a>{{item.director}}</p>
          <p><a style="font-weight: bold;color:#00bfff ;">主演：</a>{{item.actor}}</p>
          <p><a style="font-weight: bold;color:#00bfff ;">标签</a>：{{item.tag}}</p>
          <p><a style="font-weight: bold;color:#00bfff ;">概要：</a>{{item.summary}}</p>
          <div class="categories">
            <span class="tag-group__title" style="font-weight: bold;color:#00bfff ;">类型：</span>
            <el-tag
                style="margin: 3px"
                :key="category.id"
                effect="dark"
                v-for="category in (item.genre?item.genre.split(',').map(el => el.trim()).filter(item => item.trim() != ''):0)"
            >
              {{category}}
            </el-tag>
          </div>
        </div>
      </el-main>
    </el-container>
    <el-footer>
      <el-pagination
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="movies.length">
      </el-pagination>
    </el-footer>
  </el-container>
</template>

<script>
export default {
  name: "SearchView",
  data(){
    return{
      keywords: '',
      currentPage : 1,
      pageSize : 10,
      movies : []
    }
  },
  methods: {
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
    },
  }
}
</script>

<style lang="scss" scoped>
  div.movieInformation {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    border-bottom: 3px dashed rgb(58, 138, 163);
  }
  img {
    width: 138px;
    height: 207px;
  }

  p {
    margin: 2px;
    font-size: 15px;
    color: white;
    text-align: justify; 
    text-justify: newspaper;
    word-break: break-all; 
  }

  a.movie-rate {
    margin-left:25px;
    font-size: 15px;
    color: #f9ca05;
    display: inline-block
  }
  span.movie-rate-span {
    font-style: italic;
    font-size: 25px;
    margin-left:5px;
    color: #f27100;
    display: inline-block
  }
  span {
    font-size: 15px;
    color: white;
  }

  ::v-deep li.number.active{
    background: transparent !important;
  }

  ::v-deep button.btn-prev {
    background: transparent !important;
  }

  ::v-deep button.btn-next {
    background: transparent !important;
  }

</style>
