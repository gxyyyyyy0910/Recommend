<template>
    <div class="nav" style="padding: 0;" v-if="!$route.meta.ishome" :style="{backgroundImage:'linear-gradient(to right, rgba(255,255,255,0), rgba(255,255,255,0.7)),url(' + require('../../assets/images/login.jpg') + ')'}" >
      <div class="nav-logo" style="position: absolute;width: 20%;top:0px">
        <a  class="logo" href="/">
          <img src="../../assets/images/logo.png" alt=""/>
        </a>
      </div>
      <div class="zw" style="width: 20%;">
      </div>
      <div class="nav-left" style="width: 40%;padding-right: 30px">
        <el-menu
          theme="dark"
          mode="horizontal"
          router
          background-color=transparent
          text-color=white
          active-text-color=white>
          <el-menu-item index="/movieset">热门电影榜</el-menu-item>
          <el-menu-item index="/recommand">电影推荐</el-menu-item>
          <el-menu-item index="/friends">用户推荐</el-menu-item>
          <el-menu-item index="/profile">我的画像</el-menu-item>
        </el-menu>
      </div>
      <div class="nav-search" style="width: 35%;padding-top: 15px;margin-left: 30px">
      <el-input
          @keyup.enter.native="searchClick"
          placeholder="输入电影、导演、演员名称..."
          prefix-icon="el-icon-search"
          size="small"
          style="width: 70%;margin-right: 10px"
          v-model="keywords">
      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>
    <div class="nav-sep" style="width: 1%;">
      <h3 style="color: white">|</h3>
    </div>
    <div v-if="!$store.state.user.username" class="nav-right" style="width: 15%">
      <el-menu
          theme="dark"
          class="nav-right"
          router
          mode="horizontal"
          background-color=transparent
          text-color=white
          active-text-color=white>
        <el-menu-item el-menu-item index="/register">注册</el-menu-item>
        <el-menu-item index="/login">登录</el-menu-item>
      </el-menu>
    </div>
    <div v-else class="user-profile" style="width: 15%">
        <el-dropdown
            @command="handleCommand"
        >
          <span class="el-dropdown-link">{{$store.state.user.username }}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="history">历史记录</el-dropdown-item>
            <el-dropdown-item command="privacy">隐私设置</el-dropdown-item>
            <el-dropdown-item command="logout">
              <el-button type="primary" @click="logout">退出</el-button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      <el-avatar :size="40" icon="el-icon-user-solid"></el-avatar>
    </div>
  </div>
  <div class="nav" style="padding: 0;" v-else-if="$route.meta.ishome" >
      <div class="nav-logo" style="position: absolute;width: 20%;top:0px">
        <a class="logo" href="/">
          <img src="../../assets/images/logo.png" alt=""/>
        </a>
      </div>
      <div class="zw" style="width: 20%;">
      </div>
      <div class="nav-left" style="width: 40%;padding-right: 30px">
        <el-menu
          theme="dark"
          mode="horizontal"
          router
          background-color=transparent
          text-color=white
          active-text-color=white>
          <el-menu-item index="/movieset">热门电影榜</el-menu-item>
          <el-menu-item index="/recommand">电影推荐</el-menu-item>
          <el-menu-item index="/friends">用户推荐</el-menu-item>
          <el-menu-item index="/profile">我的画像</el-menu-item>
        </el-menu>
      </div>
      <div class="nav-search" style="width: 35%;padding-top: 15px;margin-left: 30px">
      <el-input
          @keyup.enter.native="searchClick"
          placeholder="输入电影、导演、演员名称..."
          prefix-icon="el-icon-search"
          size="small"
          style="width: 70%;margin-right: 10px"
          v-model="keywords">
      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>
    <div class="nav-sep" style="width: 1%;">
      <h3 style="color: white">|</h3>
    </div>
    <div v-if="!$store.state.user.username" class="nav-right" style="width: 15%">
      <el-menu
          theme="dark"
          class="nav-right"
          router
          mode="horizontal"
          background-color=transparent
          text-color=white
          active-text-color=white>
        <el-menu-item el-menu-item index="/register">注册</el-menu-item>
        <el-menu-item index="/login">登录</el-menu-item>
      </el-menu>
    </div>
    <div v-else class="user-profile" style="width: 15%">
        <el-dropdown
            @command="handleCommand"
        >
          <span class="el-dropdown-link">{{$store.state.user.username }}</span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="history">历史记录</el-dropdown-item>
            <el-dropdown-item command="privacy">隐私设置</el-dropdown-item>
            <el-dropdown-item command="logout">
              <el-button type="primary" @click="logout">退出</el-button>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      <el-avatar :size="40" icon="el-icon-user-solid"></el-avatar>
    </div>
  </div>
</template>

<script>
import '../../assets/my-ele-css/my-input.css'
export default {
  name: 'NavMenu',
  data() {
    return {
      keywords: '',
      activeIndex: '1',
      activeIndex2: '1'
    };
  },
  methods: {
    searchClick() {
      this.$emit("showSearchResult")
    },
    logout() {
      this.$axios.get('/api/user/logout').then(resp=>{
        if (resp.data.code === 200) {
          this.$store.commit('logout')
          this.$router.replace('/')
        }
        else{
          this.$alert(resp.data.message, '提示', {
            confirmButtonText: '确定'
          })
        }
      })
      .catch(failResponse => {
          this.$alert(failResponse.response.data)
      })
    },
    handleCommand(command) {
      if (command == 'history') {
        this.$router.replace('/history')
      }
      else if (command == 'privacy') {
        this.$router.replace('/privacy')
      }
    }
  },
}
</script>

<style lang="scss" scoped>
    
    ::v-deep ul.el-menu--popup-bottom-start{
        background-color: rgba(2, 10, 14, 1) !important;
      }
      .nav {
      width:100%;
      height: 70px;
      display: flex;
      display: -webkit-flex;
      flex-direction: row;
      flex-wrap: nowrap;
      justify-content: flex-start;
      align-items: flex-start
    }

    .el-menu {
      border: none !important;
    }

    .el-menu-item:hover {
      background-color: #3a91ba !important;
    }

    .el-menu-item {
      border-bottom: none !important;
      background-color: transparent !important;
    }
    .logo img{
      background:no-repeat;
    }

    .nav-search {
      display: -webkit-flex;
      display: flex;
      -webkit-align-items: center;
      align-items: center;
      -webkit-justify-content: center;
      justify-content: center;
    }

    .el-dropdown-menu__item {
      text-align: center;
    }

    .user-profile {
      height: 60px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
    .el-dropdown-link {
      color:white;
      font-size: 15px;
      padding-right: 10px;
      cursor: pointer;
    }

</style>

