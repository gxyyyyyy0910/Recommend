<template>
  <div id="poster">
    <div class="movieSetBG">
      <el-container style="background: rgba(109,114,120,0.5);margin:20px 20px">
        <el-header height="30%" v-if="categoryView">
          <movies-nav @categorySelect="listByCategory" ref="categories"></movies-nav>
        </el-header>
        <el-main v-if="categoryView">
          <movies-view ref="movieView"></movies-view>
        </el-main>
        <el-main v-else>
          <movies-view ref="movieView"></movies-view>
        </el-main>
    </el-container>
  </div>
</div>
</template>

<script>
import MoviesNav from "@/components/moviepage/MoviesNav";
import MoviesView from "@/components/moviepage/MoviesView";
import myBus from '@/assets/myBus.js'

export default {
  name: "MovieSet",
  components: {MoviesView, MoviesNav},
  data() {
    return{
      categoryView: true,
      cid:0,
      popularOrRate:0
    }
  },
  methods: {
    listByCategory() {
      this.categoryView = true
      const _this = this
      const cid = _this.$refs.categories.cid
      myBus.$emit('msg', cid)
      const popularOrRate = _this.$refs.categories.popularOrRate
      myBus.$emit('msg2', popularOrRate)
      const url = 'http://localhost:9090/movieinfo/getMovieByGenre/' + cid + '/' + popularOrRate
      console.log(url)
      _this.$axios.post(url,"pageNum="+this.$refs.movieView.currentPage).then(resp=>{
        if (resp && resp.status === 200) {
          this.$refs.movieView.movies = resp.data.list
          this.$refs.movieView.total = resp.data.total
          console.log(this.$refs.movieView.movies)
        }
      })
      .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
    },
  }
}
</script>

<style scoped>
#poster {
    background: black;
    height: 100%;
    width: 100%;
    background-size: cover;
  }
</style>