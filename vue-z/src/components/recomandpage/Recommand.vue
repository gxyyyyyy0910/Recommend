<template>
  <div>
    <div id="poster">
      <el-container>
        <el-header height="20px"></el-header>
        <el-container>
          <span style="color: #409EFF;font-size:50px;line-height:50px;font-family:'Times New Roman',serif;font-weight: 900;margin-left: 150px;margin-top:50px">猜你喜欢</span>
        </el-container>
      </el-container>
    </div>
    <div class="btn_switch">
      <button class="btn_anniu" @click="change(0)" :class="{ newStyle:0===number}"><img class="icon-arrow-left" alt="" src="../../assets/ico/arrowLeft.png" @click="$refs.slidesView.previous()"/></button>
      <button class="btn_anniu" @click="change(1)" :class="{ newStyle:1===number}"><img class="icon-arrow-right" alt="" src="../../assets/ico/arrowRight.png" @click="$refs.slidesView.next()" /></button>
    </div>
    <div v-show="0===number" style="margin-left: 25px;">
      <el-container>
        <el-header height="20px"></el-header>
        <el-container>
          <el-aside width="60%" style="padding-left:50px;padding-right: 30px;">
            <vueper-slides
                fade
                class="no-shadow thumbnails"
                ref="slidesContent"
                @slide="$refs.slidesView.goToSlide($event.currentSlide.index, { emit: false })"
                fixed-height="100%"
                :bullets="false"
                :touchable="false"
                :arrows="false">
              <vueper-slide
                  v-for="(slide, i) in slides"
                  :key="i"
              >
                <template v-slot:content>
                  <div class="slide-content" align="left">
                    <p class="count">NO.{{i+1}} {{slide.name}}</p>
                    <div class="rateAndType">
                      <rate
                          :grade=slide.rate
                          style="margin-right: 20px"
                      >
                      </rate>
                      <el-tag
                          style="margin: 3px"
                          :key="category.id"
                          effect="dark"
                          v-for="category in slide.genre.split(',').map(el => el.trim()).filter(item => item.trim() != '')"
                      >
                        {{category}}
                      </el-tag>
                    </div>
                    <div class="movie-profile">
                      <p>导演：{{slide.director}}</p>
                      <p>主演：{{slide.actor}}</p>
                      <p>标签：{{slide.tag}}</p>
                      <p>概要：{{slide.summary}}</p>
                    </div>
                  </div>
                </template>
              </vueper-slide>
            </vueper-slides>
          </el-aside>
          <el-main>
            <div class="main-slide">
              <div class="top-n slides">
                <vueper-slides
                    ref="slidesView"
                    :autoplay="false"
                    @slide="$refs.slidesContent.goToSlide($event.currentSlide.index, { emit: false })"
                    3d
                    fixed-height="380px"
                    :arrows="false"
                    :bullets="false"
                    style="width: 200px;"
                >
                  <vueper-slide
                      v-for="(slide,i) in slides"
                      :key="i"
                      :image="slide.cover"/>
                </vueper-slides>
              </div>
            </div>
          </el-main>
        </el-container>
      </el-container>
    </div>
    <div v-show="1===number" style="margin-left: 95px;margin-top: 100px">
      <el-row style="height: 950px;">
        <el-tooltip effect="dark" placement="right"
                    v-for="item in slides"
                    :key="item.id"
        >
          <h1 slot="content" style="font-size: 24px;margin-bottom: 6px;">{{item.name}}</h1>
          <div slot="content">
            <rate
                :grade=item.rate
            >
            </rate>
          </div>
          <p slot="content">导演：{{item.director}}</p>
          <p slot="content">主演：{{item.actor}}</p>
          <p slot="content">标签：{{item.tag}}</p>
          <p slot="content">观影人数：{{item.popular}}</p>
          <div class="categories" slot="content">
            <span class="tag-group__title">类型：</span>
            <el-tag
                style="margin: 3px"
                :key="category.id"
                effect="dark"
                v-for="category in (item.genre?item.genre.split(',').map(el => el.trim()).filter(item => item.trim() != ''):null)"
            >
              {{category}}
            </el-tag>
          </div>
          <p slot="content" style="width: 300px" class="abstract">简介：{{item.summary}}</p>
          <el-card style="background-color:transparent;border:none;width: 135px;margin-bottom: 10px;height: 300px;float: left;margin-right: 25px" class="movie"
                   bodyStyle="padding:10px" shadow="hover">
            <div class="cover">
              <img :src="item.cover" alt="海报" referrerPolicy="no-referrer">
            </div>
            <div class="title" style="padding-left: 10px;">
              <a href="">{{item.name}}</a>
              <span class="rate" style="font-size: 15px;color: #f9ca05;margin-left: 5px;margin-top: 2px">{{item.rate}}</span>
            </div>
          </el-card>
        </el-tooltip>
      </el-row>
    </div>
    <div v-show="0===number" style="margin-left: 100px;margin-top: 10px;">
      <el-carousel :autoplay="false" type="card" height="700px">
        <el-carousel-item
          v-for="item in slides"
          :key="item.id"
          style="background-color: rgba(0,0,0,0.9);"
        >
          <div style="width: 100%;height: 100%">
            <img :src="item.cover" style="width: 30%;height: 30%"/>
            <h1 style="font-size: 24px;margin-bottom: 6px;color:aliceblue">{{item.name}}</h1>
            <div>
              <rate :grade=item.rate>
              </rate>
            </div>
            <p><span class="tag-group__title" style="color:aliceblue;font-weight: 900;">导演：</span>{{item.director}}</p>
            <p><span class="tag-group__title" style="color:aliceblue;font-weight: 900;">主演：</span>{{item.actor}}</p>
            <p><span class="tag-group__title" style="color:aliceblue;font-weight: 900;">标签：</span>{{item.tag}}</p>
            <p><span class="tag-group__title" style="color:aliceblue;font-weight: 900;">观影人数：</span>{{item.popular}}</p>
            <div class="categories" slot="content">
              <span class="tag-group__title" style="color:aliceblue;font-weight: 900;">类型：</span>
              <el-tag
                  style="margin: 3px"
                  :key="category.id"
                  effect="dark"
                  v-for="category in (item.genre?item.genre.split(',').map(el => el.trim()).filter(item => item.trim() != ''):null)"
              >
                {{category}}
              </el-tag>
            </div>
            <p style="width:80%;margin-left: 10%;" class="abstract"><span class="tag-group__title" style="color:aliceblue;font-weight: 900;">简介：</span>{{item.summary}}</p>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>
  </div>
</template>

<script>
import { VueperSlides, VueperSlide } from 'vueperslides'
import 'vueperslides/dist/vueperslides.css'
import Rate from "@/components/common/Rate"
import '../../assets/my-ele-css/my-loading.css'
export default {
  name: "Recommand",
  components: {VueperSlide, VueperSlides,Rate},
  data: () => ({
    isCollected: false,
    slides: [],
    currPage:0,
    pageSize:1,
    pages:15,
    number: 0,
  }),
  methods: {
    loadMovies () {
      const _this = this;
      if (_this.currPage === _this.pages) {
        this.currPage = 0;
      }
      this.$axios.get('http://localhost:9090/movie/movieRecommend').then(resp => {
        if (resp.status === 200) {
          _this.slides = resp.data
        }
      }).catch(failResponse => {
        this.$alert(failResponse)
      })
    },
    change: function (index) {
      this.number = index;
    },
  },
  created() {
  },
  mounted() {
    const loading = this.$loading({
      lock: true,
      text: '正在为您生成电影推荐',
      spinner: 'el-icon-loading',
      background: 'rgba(0, 0, 0, 1)'
    });
    setTimeout(() => {
      loading.close();
    }, 1000);
    this.currPage += 1;
    this.loadMovies();
  }
}
</script>

<style lang="scss" scoped>
.blank{
  width: 5%;
  float:right;
  display: flex;
  display: -webkit-flex;
}
  p {
    margin: 2px;
    color: #c5d6df;
    overflow: hidden;
    display: -webkit-box;
    -webkit-line-clamp: 6;
    -webkit-box-orient: vertical;
  }

  div.slide-content p.count {
    font-size: 35px;
    margin-top: 20px;
    margin-bottom: 10px;
  }

  div.slide-content p{
    font-size: 20px;
  }

  .function img:hover {
    transform: scale(1.2);
  }

  .function img {
    vertical-align: middle;
  }

  .function {
    margin-top: 40px;
    margin-bottom: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .function span {
    color:#c5d6df;
    padding-right: 70px;
    vertical-align: middle;
    padding-left: 15px;
  }

  .main-slide {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .arrows img:hover {
    transform: scale(1.2);
  }
  .rateAndType {
    display: flex;
    align-items: center;
  }
  a {
    text-decoration: none;
    height: 50px;
  }

  a:link, a:visited, a:focus {
    color:#c5d6df;
  }
  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  .cover img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }
  .btn_anniu{
    width: 50%;
    padding: 25px 0;
    font-size: 29px;
    font-weight: bold;
    border: 0 solid #fff;
    color: #000;
    outline: none;
    background: #fff;
  }
  .newStyle{
    border-bottom: 2px solid #f0892e;
    color: #f0892e;
    font-size: 29px;
    font-weight: bold;
  }
  .btn_switch{
    width: 10%;
    height:25px;
    display: flex;
    display: -webkit-flex;
    float:right;
  }
</style>


