<template>
    <div>
        <div>
          <el-container>
          <span style="color: #409EFF;font-size:50px;line-height:50px;font-family:'Times New Roman',serif;font-weight: 900;margin-left: 150px;margin-top:50px">猜你想认识</span>
          </el-container>
          <div style="height: 50px"></div>
        </div>
        <el-row>
            <el-col class="col"
                    v-for="(friend,i) in friends"
                    :key="i"
                    :offset="3"
                    :span="6">
                <el-card class="box-card">
                    <div style="padding: 14px;">
                        <div class="card-header">
                            <span>用户信息</span>
                            <el-dropdown size="medium" split-button type="primary">
                                详情
                                <el-dropdown-menu >
                                    <el-dropdown-item @click.native.prevent="Jump1(friend.userID)">观影记录</el-dropdown-item>
                                    <el-dropdown-item @click.native.prevent="Jump2(friend.userID)">用户画像</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                        <div style="height: 5px"></div>
                        <div class="contentInfoStyle">
                            <div class="projectNameStyle">用户名:{{friend.userID}}</div>
                            <div class="projectNameStyle">用户偏好电影:{{friend.tag}}</div>
                        </div>
                    </div>
                </el-card>
                <br/>
            </el-col>
        </el-row>
    </div>
</template>
 <script>
     export default {
         name: 'Friends',
         data: () => ({
           friends: [],
         }),
         methods: {
             Jump1 (param1) {
               let value1=0
               this.$axios.get('http://localhost:9090/user/getprivacyhh/'+param1).then(resp => {
                 if (resp.status === 200) {
                    value1 = resp.data
                   if(value1===0){
                     this.$alert('抱歉，该用户不允许查看观影记录！！！', '提示', {
                       confirmButtonText: '确定',
                     });
                   }
                   else {
                     this.$router.push({path:'/history1',query: {ID:param1}});
                   }
                 }
               }).catch(failResponse => {
                 this.$alert(failResponse)
               })
             },
             Jump2 (param1) {
               let value2=0
               this.$axios.get('http://localhost:9090/user/getprivacypp/'+param1).then(resp => {
                 if (resp.status === 200) {
                   value2 = resp.data
                   if(value2===0){
                     this.$alert('抱歉，该用户不允许查看用户画像！！！', '提示', {
                       confirmButtonText: '确定',
                     });
                   }
                   else {
                     this.$router.push({path:'/profile1',query: {ID:param1}});
                   }
                 }
               }).catch(failResponse => {
                 this.$alert(failResponse)
               })
             },
           loadFriends () {
             const _this = this;
             this.$axios.get('http://localhost:9090/usera/friendsRecommend').then(resp => {
               if (resp.status === 200) {
                 _this.friends = resp.data
               }
             }) 
             .catch(failResponse => {
              this.$alert(failResponse.response.data)
            })
           },
         },
       mounted() {
         const loading = this.$loading({
           lock: true,
           text: '正在为您生成相似用户推荐',
           spinner: 'el-icon-loading',
           background: 'rgba(0, 0, 0, 1)'
         });
         setTimeout(() => {
           loading.close();
         }, 2000);
         this.loadFriends();
       }

     };
 </script>
<style scoped>
    .projectNameStyle {
        line-height: 20px;
        font-weight: 500;
    }
    .contentInfoStyle {
        padding: 14px;
        text-align: left;
        line-height: 40px;
    }
    .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .text {
        font-size: 14px;
    }

    .item {
        margin-bottom: 0px;
        margin-top: 10px;
    }

    .box-card {
        width: 480px;
    }
</style>
