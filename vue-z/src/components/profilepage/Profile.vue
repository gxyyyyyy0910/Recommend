<template>
  <div>
        <div
        id="mywordcloud"
        :style="{ width: '100%', height: '600px' }"
        :data="datalist"
        ></div>
        <div id="leiDaTu" class="echart" :style="{width: '100%', height: '800px'}"></div>
  </div>
</template>
<script>
import * as echarts from "echarts";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import '../../assets/my-ele-css/my-loading.css'
export default {
  name: "Profile",
  data() {
    return {
      datalist:[
        {
          name:"",
          value:0
        },
      ],
      data:[
      ],
      temp:[]
    };
  },
  methods: {
    loadWordCloud: async function () {
      let username = this.$store.state.user.username
      await this.initSign(username)
      await this.initWordCloud(username)
      await this.order(username)
    },
    async order(username){
      this.initRate(username)
    },
    initSign(username){
      this.$axios.get('http://localhost:9090/usera/createTags/'+username).then((res)=>{
        if (res && res.status === 200){
          let result = res.data
          if (result[0] ==="") {
            return
          }
          for (let i = 0; i < result.length; i++) {
            this.datalist.push({
              name: result[i],
              value: 1
            })
          }
          console.log(this.datalist)
          for (let i = 0; i < result.length; i++) {
            this.data.push({
              avgRate: 0,
              name: result[i],
              value: 1
            })
          }
          console.log(this.data)
          this.initChart();
          this.initRadar();
        }
      });
    },
    initWordCloud(username){
      this.$axios.get('http://localhost:9090/user/createWordCloud/' + username).then((res) => {
        //younghoo March_Liu bucunzai
        Object.assign(this.$data, this.$options.data())
        var result = res.data
        
        if (result.length ===0) {
            return
          }
        let num = 150
        for (let i = 0; i < result.length; i++) {
          this.datalist.push({
            name: result[i].word,
            value: result[i].frequency,
            avgRate:0
          })
          if (i === num) break;
        }
        this.initChart();
      });
    },
    initRate:function(username) {
      this.$axios.get('http://localhost:9090/user/createAvgList/'+username).then((res) => {
        //younghoo
        //Object.assign(this.$data, this.$options.data())
        //new Promise(function (resolve, reject){
        console.log("1st")
        var result = res.data
        if (result[0] ==="") {
            return
          }
        try{
          for (let i = 0; i < result.length; i++) {
            this.data.push({
              avgRate: result[i],
              name: 0,
              value: 0
            })
          }
        }
        catch (e) {
          console.log("initRate_e")
          console.log(e)
        }
        //console.log(this.data)
        //this.initValue(username)
        console.log(this.data)
        return this.data
      }).then((res1) => {
        console.log(res1)
        console.log(res1.length)
        console.log(this.data.length)
        //setTimeout(()=>{
          this.$axios.get('http://localhost:9090/user/createRadar/' + username).then((res) => {
            console.log("里面的两个")
            console.log(res1.length)
            console.log(this.data.length)
            //younghoo
            //Object.assign(this.$data, this.$options.data())
            //new Promise(function (resolve, reject) {
            console.log("2nd")
            //var result = res.data
            let result = res.data
            if (result[0] ==="") {
            return
          }
            try {
              for (let i = 0; i < result.length; i++){
                this.temp.push({
                  name: result[i].word,
                  value: result[i].frequency
                })
                this.$set(res1, i, {
                  avgRate: res1[i].avgRate,
                  name: this.temp[i].name,
                  value: this.temp[i].value
                })
              }
            } catch (e) {
              console.log("initValue_e")
              console.log(e)
            }
            return res1
            //this.initRadar();
          }).then(res2=>{
            for (let i = 0; i < res2.length; i++){
              this.$set(this.data, i, {
                avgRate: res2[i].avgRate,
                name: res2[i].name,
                value: res2[i].value
              })
            }
            console.log("3rd")
            this.initRadar();})
        //},1000)
    })//.then(res=>{
      // console.log("3rd")
      // this.initRadar();})
    // })
    },
    initValue:function (username) {
      return this.$axios.get('http://localhost:9090/user/createRadar/' + username).then((res) => {
          //younghoo
          //Object.assign(this.$data, this.$options.data())
          //new Promise(function (resolve, reject) {
          var result = res.data
          if (result[0] ==="") {
            return
          }
          try {
            for (let i = 0; i < result.length; i++) {
              if(this.data.length !== result.length){
                console.log("length")
                console.log(this.data.length)
                console.log(result.length)
                break
              }
              this.$set(this.data, i, {
                avgRate: this.data[i].avgRate,
                name: result[i].word,
                value: result[i].frequency
              })
            }
          } catch (e) {
            console.log("initValue_e")
            console.log(e)
          }
          this.initRadar();
        });
      //})
    },
    initChart() {
      this.chart = echarts.init(document.getElementById("mywordcloud"));
      //const maskResource = new Image();   /*自定义形状关键点1*/
      //maskImage.src = 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAAAKO0lEQVR4Xu2dC8weRRWGn0oUEIGCRYsKWBRFLV4QBYOiKFRFripNxXqtICICio0YuYiQgKZYrnIJhoCICgG12CrYqKAWlFivoIj1gihGRQraeFfyfu7+/v36XWZmZ3dnvj0n+dPSf+bMOe+87M7MnnNmBiadRmBGp70352mbAM8EtgPmAI8C7gHuBn4DrJmw+XkMsEPh028LH1t3sQ0CPAF4JzAf2HEMAl8HvgB8FrirdbTcDXg6sA/wEkB/3x7YdEB3+XUt8El31XFbNkmA2cAZwJsDXfgxcAlwMfDXQB11dpsJHA8sArb1HEjkfi+wzLNf5eZNEeBwYAmwRWWL4Y/A+cB5wJ8i6KuqQj69u5j8zSsq+zjwtoo6vLo3QYCzgWO9rHJr/ABwYkEGtx7xW720eHzr6RZL9No7CLg/lsJReuomgB7XR9TsyA+BtwO31DxOv/ozgffVNOZqQOQSyWuVOgkgcARSU6L370cbGOyxwOeB3Wse6zvA3sCf6xynLgIc0MaCBrgAOLpGwHYtdiW+i7xQk1YArwrt7NKvDgIInDsArYrbEG0bRcDYcnCxHY2td5w+LZ4Xj2sU+vs6CPAl4OWhBkXq903glREfn68HroxkW4iafYGVIR3H9YlNgEOBq8cN2tDvvwu8LMJqWvv6SxuyedgwvwaeWsf5R0wC6KTrp4BO+lKRVcCeFYxpay0zyORaXgUxCXAycGoFsOvqehWgR7ivaMGnPfkjfTvW2H4X4Ecx9ccigD50/BzYLKZxEXUdU5wcuqrcGbgZ2Ma1Q0PttLZ5YcyxYhFA26+jYhpWg665wO0OekVmtZvl0LaNJocBn4o1cAwCbA3o8+bGsYwyPSMR0JP2KcC/Y+AUgwBNn/jF8Dt3Hfq4FmVnEoMAv5wW6JA7sLnYr22hYgwqS1UC7Acsr2yFKQhBYGGMQJKqBFBEi45ITZpH4HvAc6oOW4UA2iL9vqoB1r8SAgo5u6mKhioEOA5YWmVw61sZgU8Dr6uipQoBvgU8v8rg1rcyAv8EdG6xNlRTKAF03q+VqEn7CCjc7txQM0IJYHv/UMTj96u0GAwlwG3AbvF9MY2BCDyjCMLx7h5CAB1A/Mp7JOtQJwIfCQ1QDSGA3jkK9TZJB4Hgk8EQAtwIKETJJC0EXlx8wvayypcACo54ENjIaxRr3AQCQRHRvgRIKUSqCVBzGkOnsspZ8BJfAnwMeIfXCNa4SQSUSPI1nwF9CaDFRkpBnz6+dqGtUvGO9HHUhwBPzixH3weHSWmrbGkdDTtHC/kQQDF/WmiYpI2AciG+4mqiDwGUEHmgq2Jr1xoCOqNRvQIncSWAtn1KVU417NvJ2Y40UnKOsoicxJUAyq75hpNGa5QCAqq99AsXQ1wJ8AHgdBeF1iYJBFRv6CwXS1wJkELGr4s/1uZ/CChYZw8XMFwI8LDi/a86fib5IKA6Db8bZ64LAVQK5dZxiuz3ySHwLpcCWi4EUJLki5Jzzwwah8ANwCvGNRpHgLcCql1nkicCqjT21VGmjyLAo4GftVjrJ0/I07JaiaQqVfv3YWaNIsAVwBvS8sesCUDgQ8ApvgRQvL+2EiaTgcDQg6FhT4Drgf0nw3fzoihv9+pBSAwigEKMo9ahsSlIAoGBeYSDCPCZopZ/ElabEdEQ+D7w7H5t/QTYCfgJoNM/k8lDYB7w5elu9RNAxZadvyVPHj4T75G+6aiC6pRMJ4D+rrNjhRSZTC4COhfQ7Ss9mU4AJRZ4RZROLkYT7Zkiu3Vn0wYEuKi4eGGivTfnepFdyh/onQ6WTwCFfP0B2MoA6gQCurjr8ukEsJCvTsz7lJOKGlb08NQTwAo+dIsAyhvQx74HyleAHf12iwDyVh/6riwJoIwSe/93iwQqOH2YCGBn/92a+NJbhY3vKAIsiFl+vJtYZuv1ViJAqjd9ZItqRobPEwF0G1bIlSoZ+WmmDkHgOBHg28DzDKJOInCOCHA3sF0n3TenrxcB7gN07YtJ9xBYLQL8t3t+m8cFAmuMAN3mwn1GgG4ToPcxaF1it2N2fEoadX+dCGC3fjWKeVKD3SsCWOn3pOakUWPuFAFW9EeKNmqCDdYmAreJAAoNemObVtjYrSFwnQhwEqAMUpPuIXCaCKBEAb0GTLqHwAIRYCZwf/d8N4+BXcqQMN0BFOUyYoM1GwR0/rNlSYBrgYH549m4Y4b6IrAMOKgkwDHAOb4arH3WCPTKyJUEeDxwT9bumPG+COwM9A6CSlkFvMBXi7XPEgHVgHiaLJ9OALsNPMu5DDJ6MbCknwCzgXuD1FmnnBD4D6C5VjLwek8A/fdNwF45eWO2eiPwOeCQsld/iZiDi5Ji3lqtQzYIPBdYPYwAIsQaYE427pihPgjoCa9ycVPS/wTQLw4HLvHRam2zQUB3Pq8cR4BHALog0opFZTOvToYuH1T9ddATQNpOAM5wUmuNckHgSYCqh68nwwigRioVr04m+SPw4eJ/6g08GUUALRZGXjaQPy6d8ECnfs8C/jHI21EEUPvLAFWUMskTgX8B2vb9YJj54wigsjFikC0I8yTA+4EzR5k+jgDq+1rgmjz977TVen3rzqCR4kIAKbjQ9176cQPb72tFYG3xtS/KvYGy9OFFAokWEyZpI6AagDrwcVrAuz4B5PITi8XE5mn733nrjgYucEXBhwDSeQCgWDKTNBFYrxK4i4m+BJDOE4HTXJRbm0YRWAq8x3fEEAJoDEWTHO87mLWvDQFt9bTl85ZQAmig8wC9b0zaReCDwKmhJlQhgMa8FFgUOrj1q4zAVGxfqKaqBDAShCJfvZ8yuj9RVU0MAsiGY4Gzqxpj/Z0Q+BtwYP/1b049BzSKRQCp3ge42srOh06FUz/l8wnnW51aOzSKSQANp8OiLwLKOjGJi8DtwGuUzRNTbWwCyLYtipOohTEN7bguvV5rudCzDgKUczW/2CXY0XE4exXC9Rbg5nAVo3vWSQCNrKRTrVT3rsuBCdYr3I4AtOirTeomQGm4jihPBzatzZPJUazq7Zr4G5pwqSkCyJdtisDEo4BNmnAuszFUpkffWfRBpzFpkgClUwovU2UyJaBs3JinaQ90BaDs7MZrNbVBgHIqtD4Q449Me25qtU6RVucDd9Q6ygjlbRKgNEupyrq5VKvdLdsCosFx/wJcDChWv5ei3aakQIDp/h9aEEG1CydN7gLOKi7p0oleEpIaAUpQti3K176pLGWSBFr+RjwIKCfvojr38v5m/b9HqgSY7tPugA6V9FTo1bVJXHRkqzD6G4FbErd1gwohqdur9cJ+wDxgD2CHBAzWQY0mWrn3VwF61GcjOTwBRoGp285EBP3sBsxt4Ao8pc5rwvWjymq6dzFbyZ0Ag4DfrHhV6HWxU0GIxxV/auupj1XDRIkUOn/XVXrak+tHK3X9m7Kl9SVO7/WJkUkkgMvkzCqIIDLoY5UmXJNc67m7i2FNt+kqAZrGOdnxjADJTk0zhhkBmsE52VEeAmv0SlvfFLheAAAAAElFTkSuQmCC'
      //;
      const option = {
        series: [
          {
            type: "wordCloud",
            //用来调整词之间的距离
            gridSize: 12,
            // 形状 circle 圆，cardioid  心， diamond 菱形，
            shape: "circle",
            //用来调整字的大小范围
            sizeRange:[15,80],
            //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
            rotationRange:[-45,45],
            //maskImage: maskResource,
            //位置相关设置
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "100%",
            height: "100%",
            drawOutOfBound: false,
            //随机生成字体颜色
            textStyle: {
              color: function () {
                return (
                    "rgb(" +
                    Math.round(Math.random() * 256) +
                    ", " +
                    Math.round(Math.random() * 256) +
                    ", " +
                    Math.round(Math.random() * 256) +
                    ")"
                );
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: "#2ac",
              },
            },
            //数据
            data: this.datalist,
          },
        ],
      };
      //maskResource.onload = function(){   /* 自定义形状，关键点4*/
        this.chart.setOption(option);
      //};
    },
    initRadar() {
      //console.log(this.data)
      let myChart = echarts.init(document.getElementById('leiDaTu'))
      let indicator = []
      let genreCat = []
      let rateCat = []
      this.data.forEach(info=>{
        let key = info.name;
        let value = info.value;
        genreCat[key] = value
        // rateCat[key] = avg
      })
      let genreValues = Object.values(genreCat) //frequency
      let genreMax = Math.max(...genreValues)
      let genreKeys = Object.keys(genreCat) //genre name
      let max = genreMax
      console.log(max)
      let multiple = max / 5
      this.data.forEach(info=>{
        let key = info.name;
        let avg = info.avgRate;
        rateCat[key] = avg * (multiple / 1.5)
        rateCat[key] = rateCat[key].toFixed(2)
      })
      let rateValues = Object.values(rateCat)
      //let num = 15
      //genreValues = genreValues.slice(0, num)
      //genreKeys = genreKeys.slice(0, num)
      genreKeys.forEach(item=>indicator.push({
        'name':item,
        'max':max
      }))
      let result = {
        indicator:indicator,
        genreCat:genreValues
      }
      let option = {
            tooltip: {
              trigger: 'item',
              position:function(p){    //其中p为当前鼠标的位置
                return [p[0], p[1]];
              },
            },
            legend: {
              orient: 'vertical',
              right: '5%',
              textStyle: {
                fontSize: 16,
                color:'#DFE0E5',
                fontWeight: 'bold'
              },
              data: []
            },
            radar: {
              splitNumber: 4, // 雷达图圈数设置
              center: ['50%','50%'],
              radius : '65%',
              name: {
                textStyle: {
                  color: '#DFE0E5',
                  backgroundColor: '#121E36'
                }
              },
              indicator: indicator,
              axisName: { //指示器样式
                color: '#004C82', //指示器的文字颜色
                lineHeight:20,//指示器行高
                borderRadius:5,//指示器圆角
                padding:[0,3,0,3],//指示器的内边距
                fontSize: 20,
                backgroundColor:'#fff'//指示器的背景颜色
              },
              splitArea : {
                show : false,
                areaStyle : {
                  color: 'rgba(255,0,0,0)', // 图表背景的颜色
                },
              },
            },
            series: [{
              name: '评分统计',
              type: 'radar',
              data : [
                {
                  value : rateValues,
                  name : '平均评分',
                  itemStyle: {
                    normal: {
                      color:'#355C70'  //显示颜色与填充颜色对应
                    }
                  },
                  areaStyle: {
                    normal: {
                      color: '#355C70'    //填充的颜色
                    }
                  }
                },
                {
                  value : genreValues,
                  name : '类型分布',
                  itemStyle: {
                    normal: {
                      color:'#6188B7'  //显示颜色与填充颜色对应
                    }
                  },
                  areaStyle: {
                    normal: {
                      color: '#6188B7'    //填充的颜色
                    }
                  }
                },
              ]
            }]}
      myChart.setOption(option)
    },
  },
  created() {
    const loading = this.$loading({
      lock: true,
      text: '正在为您生成用户画像',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 1)'
    });
    setTimeout(() => {
      loading.close();
      this.loadWordCloud();
    }, 1000);
    this.currentPage += 1;
    //this.loadWordCloud();
  }
};
</script>

<style scoped>

</style>
