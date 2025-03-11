<template>
    <div class="movie-nav" style="margin-top: 20px">
      <div class="chooseBytype" style="display: flex;width: 100%;flex-wrap: wrap;margin-top: 10px">
        <el-radio-group
          v-model="cid"
          @change="handleSelect"
        >
        <el-radio v-for="(val,key) in types" :key="key" :label="key">{{val}}</el-radio>
      </el-radio-group>
    </div>
    <el-divider></el-divider>
    <div class="chooseByOther" style="margin-left: 15px">
      <el-radio-group
        v-model="popularOrRate"
        @change="handleSelect2"
      >
        <el-radio label="1">按评分</el-radio>
        <el-radio label="2">按观看人数</el-radio>
      </el-radio-group>
    </div>
  </div>
</template>

<script>
const categories = {
  0: '全部', 1: '剧情', 2: '喜剧', 3: '动作',
  4: '爱情', 5: '科幻', 6: '动画', 7: '悬疑',
  8: '惊悚', 9: '恐怖', 10: '犯罪', 11: '同性',
  12: '音乐', 13: '歌舞', 14: '传记', 15: '历史',
  16: '战争', 17: '西部', 18: '奇幻', 19: '冒险',
  20: '灾难', 21: '武侠', 22: '情色',23:'家庭'
};
export default {
  name: "MoviesNav",
  data() {
    return {
      popularOrRate:'1',
      cid:'0',
      types: categories
    }
  },
  methods: {
    handleSelect(value) {
      this.cid = value;
      this.$emit('categorySelect')
    },
    handleSelect2(value) {
      this.popularOrRate = value;
      this.$emit('categorySelect')
    }
  }
}
</script>

<style lang="scss" scoped>
  
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
</style>
