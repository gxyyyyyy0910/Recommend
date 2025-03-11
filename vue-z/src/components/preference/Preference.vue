<template>
  <div id="poster">
    <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleForm"
        @keyup.enter.native="submitForm('ruleForm')"
        class="register">
      <h2 style="color: #3a91ba;">兴趣选择</h2>
      <el-form-item label="个性偏好" prop="type">
        <el-row :gutter="20">
          <el-checkbox-group size="small"  v-model="ruleForm.type" autocomplete="off" >
            <el-col :span="5">
              <el-checkbox label="喜剧">喜剧</el-checkbox>
              <el-checkbox label="爱情">爱情</el-checkbox>
              <el-checkbox label="冒险">冒险</el-checkbox>
            </el-col>
            <el-col :span="5">
              <el-checkbox label="犯罪">犯罪</el-checkbox>
              <el-checkbox label="奇幻">奇幻</el-checkbox>
              <el-checkbox label="动画">动画</el-checkbox>
            </el-col>
            <el-col :span="5">
              <el-checkbox label="恐怖">恐怖</el-checkbox>
              <el-checkbox label="动作">动作</el-checkbox>
              <el-checkbox label="科幻">科幻</el-checkbox>
            </el-col>
          </el-checkbox-group>
        </el-row>
      </el-form-item>
      <el-form-item style="margin-top: 20px;">
        <el-button  type="primary" @click="submitForm('ruleForm')">提交</el-button>
        <el-button  @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  name: 'Preference',
  data() {
    return {
      ruleForm: {
        type:[]
      },
      rules: {
        type:[
          { required: true, message: '请选择电影偏好',trigger: 'change' }
        ],
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.get('api/user/preference/'+this.ruleForm.type.toString()).then(resp => {
             if (resp.data.code === 200) {
                this.$router.replace('/')
            }
             else {
                this.$alert(resp.data.message, '提示', {
                confirmButtonText: '确定'
              })
            }
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    feelingClick(item, index) {
      // 获取你点击时的索引，如果找不到这个索引会为-1
      let arrIndex = this.spanIndex.indexOf(index)
      // 如果没有这个索引，则push进去
      // 有这个索引那么删除掉（取消选中）
      // 上面:class选中颜色同理
      if (arrIndex > -1) {
        this.spanIndex.splice(arrIndex, 1)
      } else {
        this.spanIndex.push(index)
      }
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
  margin:20px auto;
  height: 450px;
  width: 300px;
  padding: 10px 20px 0px 20px;
  background: rgba(2, 10, 14, 0.5);
}

::v-deep .el-form-item__label {
  color: white;
}
::v-deep .el-checkbox__label {
  color: white;
}
</style>
