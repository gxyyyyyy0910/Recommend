<template>
    <div id="poster">
        <el-form label-position="left" class="privacy">
            <h2 style="color: #3a91ba;">隐私权限设置</h2>
            <el-divider></el-divider>
            <el-form-item label="是否允许查看观影记录？" prop="history">
                <el-switch
                        v-model="value1"
                        :active-value="1"
                        :inactive-value="0"
                        @change='changeStatush'
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
            <el-form-item label="是否允许查看用户画像？" prop="profile">
                <el-switch
                        v-model="value2"
                        :active-value="1"
                        :inactive-value="0"
                        @change='changeStatusp'
                        active-color="#13ce66"
                        inactive-color="#ff4949">
                </el-switch>
            </el-form-item>
        </el-form>
    </div>
</template>
<script>
    export default {
      name: 'Privacy',
      data(){
      return{
        //页面加载初始化为开启状态
        value1: "1",
        value2: "1"
      };
    },
        methods: {
            //该参数为当前的value值
            changeStatush: function (callback) {
                this.$axios.get('http://localhost:9090/user/privacyh/'+String(callback)).then(resp => {
                if (resp.status === 200) {
                  console.log(callback);
                }
              })
              .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
            },
            changeStatusp: function (callback) {
              this.$axios.get('http://localhost:9090/user/privacyp/'+String(callback)).then(resp => {
                if (resp.status === 200) {
                  console.log(callback);
                }
              })
              .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
            }
        },
        mounted(){
          this.$axios.get('http://localhost:9090/user/getprivacyh').then(resp => {
            if (resp.status === 200) {
              this.value1 = resp.data
            }
          }).catch(failResponse => {
            this.$alert(failResponse)
          })
          this.$axios.get('http://localhost:9090/user/getprivacyp').then(resp => {
            if (resp.status === 200) {
              this.value2 = resp.data
            }
          })
          .catch(failResponse => {
            this.$alert(failResponse.response.data)
          })
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
    .privacy {
        margin:100px auto;
        width:300px;
        padding: 10px 20px 50px 20px;
        background: rgba(2, 10, 14, 0.5);
    }

    ::v-deep .el-form-item__label {
        color: white;
    }
</style>
