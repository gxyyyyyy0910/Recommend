<template>
  <div id="poster">
    <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        @keyup.enter.native="submitForm('ruleForm')"
        class="register">
      <h2 style="color: #3a91ba;">注册</h2>
      <el-form-item label="用户名" prop="name">
        <el-input size="small" auto-complete="off" v-model="ruleForm.userID"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="pass">
        <el-input size="small" type="password" v-model="ruleForm.password" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="确认密码" prop="checkPass">
        <el-input size="small" type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
      </el-form-item>
      <el-button style="padding-right: 10px" type="text" @click="login">已有账号？登录</el-button>
      <el-form-item style="margin-top: 10px;">
        <el-button  type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button  @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
      <el-divider></el-divider>
    </el-form>
  </div>
</template>

<script>
export default {
  name: 'Register',
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.ruleForm.checkPass !== '') {
          this.$refs.ruleForm.validateField('checkPass');
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.ruleForm.password) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    return {
      ruleForm: {
        password: '',
        checkPass: '',
        userID: ''
      },
      rules: {
        password: [
          { required: true, validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        userID: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
        ]
      }
    };
  },
  methods: {
    login() {
      this.$router.replace('/login')
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post('api/user/register',JSON.stringify({userID: this.ruleForm.userID,password: this.ruleForm.password})
          ,{headers: {'Content-Type':'application/json;charset=UTF-8'}}).then(resp => {
             if (resp.data.code === 200) {
              this.$alert('注册成功', '提示', {
                  confirmButtonText: '确定'
                })
                this.$router.replace('/login')
            } else if(resp.data.code === 300){
               this.$alert('账号已存在', '提示', {
                 confirmButtonText: '确定'
               })
             }
             else {
                this.$alert(resp.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
            // eslint-disable-next-line no-unused-vars
          }).catch(failResponse => {
            this.$alert(failResponse)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
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

.register {
  margin:100px auto;
  height: 493.5px;
  width: 300px;
  padding: 10px 20px 0px 20px;
  background: rgba(2, 10, 14, 0.5);
}

::v-deep .el-form-item__label {
  color: white;
}

</style>
