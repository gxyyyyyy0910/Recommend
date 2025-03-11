<template>
  <div id="poster">
      <el-form :model="loginForm" label-position="left" class="login"
        @keyup.enter.native="login"
      >
        <h2 style="color: #3a91ba;">登录</h2>
        <el-form-item label="用户名">
          <el-input size="small" type="text" v-model="loginForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input size="small" type="password" v-model="loginForm.password" autocomplete="off"></el-input>
        </el-form-item>
        
        <el-button style="padding-right: 10px" type="text" @click="register">还没有账号？</el-button>
        <el-form-item style="padding: 10px 50px 0px 50px">
          <el-button  class="submit" size="medium" round type="primary" @click="login" >提交</el-button>
        </el-form-item>
        <el-divider></el-divider>
      </el-form>
    </div>
</template>

<script>
export default {
  name: "Login",
  data () {
    return {
      checked: false,
      loginForm: {
        username: '',
        password: ''
      },
      responseResult: []
    }
  },
  methods: {
    login () {
      const _this = this
      console.log(this.$store.state)
      const url = 'api/user/login'
      this.$axios
          .post(url, JSON.stringify({
            userID: this.loginForm.username,
            password: this.loginForm.password
          }),{headers: {'Content-Type':'application/json;charset=UTF-8'}})
          .then(successResponse => {
            if (successResponse.data.code === 201) {
              _this.$store.commit('login',_this.loginForm)
              const path = this.$route.query.redirect;
              this.$router.replace({path:path === '/' || path === undefined ? '/' : path})
            }
            if (successResponse.data.code === 200) {
              _this.$store.commit('login',_this.loginForm)
              this.$router.replace('/preference')
            }
            if (successResponse.data.code === 300) {
              this.$alert(successResponse.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
            if (successResponse.data.code === 400) {
              this.$alert(successResponse.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
    },
    register() {
      this.$router.replace('/register')
    }
  }
}
</script>

<style lang="scss" scoped>

  #poster {
    background: url("../../assets/images/login.jpg") no-repeat center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }

  .login {
    margin:100px auto;
    width:300px;
    padding: 10px 20px 0px 20px;
    background: rgba(2, 10, 14, 0.5);
  }

  ::v-deep .el-form-item__label {
    color: white;
  }

  .submit.el-button {
    width: 200px;
  }
</style>
