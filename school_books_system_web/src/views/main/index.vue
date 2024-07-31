<template>
  <div class="app-container"
       style="
    width: 100%;
    height: 100%;
    margin: -8px ;
    position: absolute;"
  >
    <el-container style="height: 100% ;border: 1px solid #eee">
      <!-- 左側Menu Start -->
      <el-aside width="220px" style="background-color: #545c64">
        <el-menu
          :default-active="defaultActiveMenu"
          class="el-menu-vertical-demo"
          unique-opened
          @open="handleOpen"
          @close="handleClose"
          @select="handleSelect"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b">
          <el-menu-item index="logo" disabled>
            <div style="text-align: center">
              <span slot="title" style="font-size: large;font-weight: 500;">學校圖書管理系統</span>
            </div>
          </el-menu-item>
          <el-menu-item index="home">
            <i class="el-icon-menu"></i>
            <span slot="title">首頁</span>
          </el-menu-item>
          <el-submenu index="2" v-if="$roleRule(['ROLE_SUPER_ADMIN'])">
            <template slot="title">
              <i class="el-icon-s-custom"></i>
              <span>管理員管理</span>
            </template>
            <el-menu-item index="admin-list">管理員列表</el-menu-item>
          </el-submenu>
          <el-submenu index="3" v-if="$roleRule(['ROLE_ADMIN'])">
            <template slot="title">
              <i class="el-icon-user"></i>
              <span>用戶管理</span>
            </template>
            <el-menu-item index="user-list">用戶列表</el-menu-item>
          </el-submenu>
          <el-submenu index="4" v-if="$roleRule(['ROLE_ADMIN', 'ROLE_USER'])">
            <template slot="title">
              <i class="el-icon-s-grid"></i>
              <span>圖書類別管理</span>
            </template>
            <el-menu-item index="bookCategory-list">類別列表</el-menu-item>
          </el-submenu>
          <el-submenu index="5" v-if="$roleRule(['ROLE_ADMIN', 'ROLE_USER'])">
            <template slot="title">
              <i class="el-icon-folder"></i>
              <span>圖書管理</span>
            </template>
            <el-menu-item index="book-list">圖書列表</el-menu-item>
          </el-submenu>
          <el-submenu index="6" v-if="$roleRule(['ROLE_ADMIN', 'ROLE_USER'])">
            <template slot="title">
              <i class="el-icon-document"></i>
              <span>圖書借閱管理</span>
            </template>
            <el-menu-item index="bookBorrow-list">借閱列表</el-menu-item>
          </el-submenu>
          <el-submenu index="7" v-if="$roleRule(['ROLE_ADMIN', 'ROLE_USER'])">
            <template slot="title">
              <i class="el-icon-s-unfold"></i>
              <span>還書管理</span>
            </template>
            <el-menu-item index="returnBook-list">還書列表</el-menu-item>
          </el-submenu>
          <el-submenu index="8" v-if="$roleRule(['ROLE_ADMIN', 'ROLE_USER'])">
            <template slot="title">
              <i class="el-icon-chat-square"></i>
              <span>公告管理</span>
            </template>
            <el-menu-item index="announcement-list">公告列表</el-menu-item>
          </el-submenu>
          <el-submenu index="9" v-if="$roleRule(['ROLE_ADMIN'])">
            <template slot="title">
              <i class="el-icon-picture-outline"></i>
              <span>輪播圖管理</span>
            </template>
            <el-menu-item index="carouselMap-list">輪播圖列表</el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 左側Menu End -->

      <el-container>
        <!-- 右側下滑菜單欄 Start -->
        <el-header style="text-align: right; font-size: 16px">
          <el-dropdown :hide-on-click="false" @command="handleCommand">
            <span class="el-dropdown-link">{{ role }}：{{ name }}<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="userInfo">個人信息</el-dropdown-item>
              <el-dropdown-item command="logOut" divided
                                v-loading="loading.loadingLogOut">登出
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-header>
        <!-- 右側下滑菜單欄 End -->

        <!-- 內容 Start -->
        <el-main>
          <home v-if="mainPage === 'home'"/>
          <user-info v-if="mainPage === 'userInfo'"/>
          <admin-list v-if="mainPage === 'admin-list'"/>
          <user-list v-if="mainPage === 'user-list'"/>
          <announcement-list v-if="mainPage === 'announcement-list'"/>
          <book-list v-if="mainPage === 'book-list'"/>
          <bookBorrow-list v-if="mainPage === 'bookBorrow-list'"/>
          <return-book-list v-if="mainPage === 'returnBook-list'"/>
          <bookCategory-list v-if="mainPage === 'bookCategory-list'"/>
          <carouselMap-list v-if="mainPage === 'carouselMap-list'"/>
        </el-main>
        <!-- 內容 End -->

        <!-- 尾部 Start -->
        <el-footer>
          <div>
            ©2024學校圖書管理系統
          </div>
        </el-footer>
        <!-- 尾部 End -->
      </el-container>
    </el-container>
  </div>
</template>

<script>

import AdminList from '@/views/admin/list'
import UserList from '@/views/user/list'
import AnnouncementList from '@/views/announcement/list'
import BookList from '@/views/book/list'
import BookBorrowList from '@/views/bookBorrow/list'
import BookCategoryList from '@/views/bookCategory/list'
import CarouselMapList from '@/views/carouselMap/list'
import ReturnBookList from '@/views/returnBook/list'
import Home from '@/views/home/index'
import UserInfo from '@/views/home/userInfo'
import AuthAPI from '../../api/auth'
import VueCookie from 'vue-cookie'

export default {
  components: {
    AdminList,
    UserList,
    AnnouncementList,
    BookList,
    BookBorrowList,
    ReturnBookList,
    BookCategoryList,
    CarouselMapList,
    Home,
    UserInfo
  },
  data () {
    return {
      // 數據中心
      role: '',
      name: '',
      loading: {
        loadingLogOut: false
      },
      mainPage: 'home',
      defaultActiveMenu: 'home'
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後獲取數據
  created () {
    let authInfo = JSON.parse(VueCookie.get('authInfo'))
    // ROLE_USER,ROLE_ADMIN,ROLE_SUPER_ADMIN
    if (authInfo.role === 'ROLE_USER') {
      this.role = '用戶'
    }
    if (authInfo.role === 'ROLE_ADMIN') {
      this.role = '管理員'
    }
    if (authInfo.role === 'ROLE_SUPER_ADMIN') {
      this.role = '超級管理員'
    }

    if (authInfo.role === 'ROLE_USER') {
      this.name = authInfo.userVO.name
    } else {
      this.name = authInfo.adminVO.nickName
    }
  },
  // 自定義方法
  methods: {
    handleOpen (key, keyPath) {
      // console.log('handleOpen', key, keyPath)
    },
    handleClose (key, keyPath) {
      // console.log('handleClose', key, keyPath)
    },
    handleSelect (key, keyPath) {
      console.log('handleSelect', key, keyPath)
      this.mainPage = key
    },
    handleCommand (command) {
      this.defaultActiveMenu = 'logo'
      if (command === 'userInfo') {
        this.mainPage = 'userInfo'
      } else if (command === 'logOut') {
        this.logOutHandler()
      }
    },
    logOutHandler () {
      this.loading.loadingLogOut = true

      AuthAPI.logOut().then(resp => {
        if (resp.code === 200) {
          this.$router.push({path: '/'})
          VueCookie.delete('Authorization')
          VueCookie.delete('userInfo')
        }
      }).finally(f => {
        this.loading.loadingLogOut = false
      })
    }
  }
}
</script>

<style scoped>
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}

.el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>
