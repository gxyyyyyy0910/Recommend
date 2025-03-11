<template>
  <div id="poster">
  <el-container style="background: rgba(109,114,120,0.5);margin:20px 20px">
    <el-main>
      <SearchView ref="movieView"></SearchView>
    </el-main>
  </el-container>
</div>
</template>

<script>
import SearchView from "@/components/searchResultPage/SearchView";
export default {
  name: "SearchResult",
  components:{SearchView},
  created() {
    let url = this.$route.query.url
    const keywords = this.$route.query.keywords
    this.$axios.get(url).then((resp) => {
      if (resp.status === 200) {
        this.$refs.movieView.keywords = keywords
        this.$refs.movieView.movies = resp.data
      }
    })
  },
  watch :{
    // eslint-disable-next-line no-unused-vars
    $route () {
      this.$router.go(0)
    }
  }
}
</script>

<style scoped>
#poster {
    background: url("../../assets/images/login.jpg") no-repeat center;
    height: 100%;
    width: 100%;
    background-size: cover;
  }
</style>
