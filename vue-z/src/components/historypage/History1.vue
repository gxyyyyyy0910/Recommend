<template>
  <div>
  <div class="history-nav" style="margin-top: 20px">
    <div class="chooseBytype" style="display: flex;width: 100%;flex-wrap: wrap;margin-top: 10px;border: 3px dashed rgb(58, 138, 163);background: rgb(61, 62, 71);">
      <el-radio-group
          v-model="cid"
          @change="handleSelect"
      >
        <el-radio v-for="(val,key) in types" :key="key" :label="key">{{val}}</el-radio>
      </el-radio-group>
    </div>
    <el-divider></el-divider>
  </div>
  <el-container>
    <el-header style="margin-bottom: 30px">
      <p v-if="movies.length" style="font-size: 35px;color: #3377aa">看过 {{keywords}} 类别的电影...</p>
      <p v-else style="font-size: 35px;color: #3377aa">暂无结果</p>
    </el-header>
    <el-container
        v-for="item in movies"
        :key="item.id"
        style="height: 300px"
    >
      <el-aside
          width="200px"
      >
        <img :src="item.cover" alt="cover" referrerPolicy="no-referrer"/>
      </el-aside>
      <el-main style="padding-top: 0px">
        <div class="movieInformation">
          <p style="font-size: 25px">{{item.name}}
            <span class="movie-rate-span">
              {{item.rate}}
            </span>
            <span style="position: absolute;left: 980px;right: 0px;font-size: 25px">
              用户打分：
            </span>
            <span class="userr" style="position: absolute;left: 1100px;right: 0px;">
              <rate
                  :grade=item.urate*2
              >
            </rate>
            </span>
          </p>
          <p>导演：{{item.director}}</p>
          <p>主演：{{item.actor}}</p>
          <p>标签：{{item.tag}}</p>
          <p slot="content">观影人数：{{item.popular}}</p>
          <p>概要：{{item.summary.substring(0,250)}}……</p>
          <div class="categories">
            <span class="tag-group__title">类型：</span>
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
          :total="total">
      </el-pagination>
    </el-footer>
  </el-container>
  </div>
</template>

<script>
import Rate from "@/components/common/Rate";
const categories = {
  0: '全部', 1: '剧情', 2: '喜剧', 3: '动作',
  4: '爱情', 5: '科幻', 6: '动画', 7: '悬疑',
  8: '惊悚', 9: '恐怖', 10: '犯罪', 11: '同性',
  12: '音乐', 13: '歌舞', 14: '传记', 15: '历史',
  16: '战争', 17: '西部', 18: '奇幻', 19: '冒险',
  20: '灾难', 21: '武侠', 22: '情色',23:'家庭'
};
export default {
  components: {Rate},
  name: "HistoryView",
  data(){
    return{
      cid:'0',
      types: categories,
      keywords: '',
      currentPage : 1,
      pageSize : 10,
      movies : [],
      total:0,
    }
  },
  methods: {
    handleCurrentChange: function (currentPage) {
      this.currentPage = currentPage
      console.log(this.currentPage)
      this.listByCategory()
    },
    handleSelect(value) {
      this.cid = value;
      this.$emit('categorySelect')
      this.listByCategory()
    },
    listByCategory() {
      let username = this.$route.query.ID
      const _this = this
      const cid = _this.cid
      _this.keywords = categories[this.cid]
      const url = 'http://localhost:9090/user/createHistory/' + cid + '/' + username
      console.log(url)
      _this.$axios.post(url,"pageNum="+this.currentPage).then(resp=>{
        if (resp && resp.status === 200) {
          this.movies = resp.data.list
          this.total = resp.data.total
        }
      })
    },
  },
  created() {
    const loading = this.$loading({
      lock: true,
      text: '正在为您生成用户历史记录',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 1)'
    });
    setTimeout(() => {
      loading.close();
      this.listByCategory();
    }, 1000);
  },
}
</script>

<style lang="scss" scoped>
.userr{
  display: inline-block;
}
div {
  display: flex;
  flex-wrap: wrap;
}
button {
  background: transparent !important;
  color: white;
  border: none;
  margin: 0 !important;
}
.el-radio {
  color: white;
}

::v-deep .chooseBytype span.el-radio__inner {
  display: none;
}

.el-radio {
  font-size: 25px;
  padding-top: 10px;
}
div.movieInformation {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
img {
  width: 138px;
  height: 207px;
}

p {
  margin: 2px;
  font-size: 15px;
  color: rgb(0, 0, 0);
  text-align: justify;
  text-justify: newspaper; 
  word-break: break-all;
}

span.movie-rate-span {
  font-style: italic;
  font-size: 25px;
  margin-left:10px;
  color: #f9ca05;
  display: inline-block
}
span {
  font-size: 15px;
  color: rgb(0, 0, 0);
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
