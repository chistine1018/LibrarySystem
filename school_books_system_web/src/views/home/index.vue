<template>
  <div class="app-container">
    <div>
      <el-carousel :interval="4000" type="card" height="350px" v-loading="loading.loadingFindData">
        <el-carousel-item v-for="url in carouselMapUrlList" :key="url">
          <el-image
            height="300"
            style="width: 100%;height: 350px"
            :src="url"
            :preview-src-list="[url]">
          </el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div style="background-color: #f0f9eb;color: #67C23A;padding: 8px 16px;">
      <div>
        <h3>最新公告：{{announcementVO.title}}</h3>
      </div>
      <div style="white-space: pre-wrap;word-break: break-all;">{{announcementVO.content}}</div>
    </div>
  </div>
</template>

<script>
// import request from '@/utils/request'
import HomeAPI from '../../api/home'
export default {
  data () {
    return {
      // 數據中心
      announcementVO: {},
      carouselMapUrlList: [],
      loading: {
        loadingFindData: false
      }
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後獲取數據
  created () {
    this.findData()
  },
  // 自定義方法
  methods: {
    findData () {
      this.loading.loadingFindData = true
      HomeAPI.home().then(resp => {
        if (resp.code !== 200) {
          return
        }
        this.announcementVO = resp.data.announcementVO
        this.carouselMapUrlList = resp.data.imgUrlList
        console.log(resp.data)
      }).finally(f => {
        this.loading.loadingFindData = false
      })
    }
  }
}
</script>

<style scoped>
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
  background-color: #d3dce6;
}
</style>
