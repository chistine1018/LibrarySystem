<template>
  <div class="app-container">
    <!--標籤-->

    <!-- 學生個人信息表單 Start -->
    <div v-if="role === 'ROLE_USER'">
      <h3>學生信息</h3>
      <div class="demo-drawer__content" style="padding: 20px 25px 0 15px;">
        <!--      内容-->
        <div v-loading="loading.loadingAuthInfo">
          <el-form label-position="right" label-width="80px" :model="userInfo">
            <el-form-item label="學號">
              <el-input v-model="userInfo.studentNumber" placeholder="請輸入學號" show-word-limit
                        maxlength="32"></el-input>
            </el-form-item>
            <el-form-item label="密碼">
              <el-input v-model="userInfo.password"
                        type="password"
                        show-password/>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="userInfo.name" placeholder="請輸入姓名" show-word-limit maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-radio-group v-model="userInfo.sex">
                <el-radio :label="1">男</el-radio>
                <el-radio :label="0">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="學院">
              <el-input v-model="userInfo.college" placeholder="請輸入學院" show-word-limit maxlength="50"></el-input>
            </el-form-item>
            <el-form-item label="班級">
              <el-input v-model="userInfo.grade" placeholder="請輸入班級" show-word-limit maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="電話">
              <el-input v-model="userInfo.phone" placeholder="請輸入電話" show-word-limit maxlength="11"></el-input>
            </el-form-item>
            <el-form-item label="宿舍">
              <el-input v-model="userInfo.dormitory" placeholder="請輸入宿舍" show-word-limit maxlength="50"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <!--      底部按鈕-->
        <div class="demo-drawer__footer" style="text-align: right">
          <el-button type="primary" @click="updateInfo" v-loading="loading.loadingUpdateInfo">確 定</el-button>
        </div>

      </div>
    </div>
    <!-- 學生個人信息表單 End -->

    <!-- 管理員個人信息表單 Start -->
    <div v-if="role === 'ROLE_ADMIN' || role === 'ROLE_SUPER_ADMIN'">
      <h3>管理員信息</h3>
      <div class="demo-drawer__content" style="padding: 20px 25px 0 15px;">
        <!--      内容-->
        <div>
          <el-form label-position="right" label-width="80px" :model="adminInfo">
            <el-form-item label="角色">
              <el-select v-model="role" placeholder="請選擇角色"
                         disabled
                         style="width: 100%">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="帳號">
              <el-input v-model="adminInfo.username" placeholder="請輸入帳號"
                        show-word-limit maxlength="20"></el-input>
            </el-form-item>
            <el-form-item label="密碼">
              <el-input v-model="adminInfo.password"
                        type="password"
                        show-password/>
            </el-form-item>
            <el-form-item label="暱稱">
              <el-input v-model="adminInfo.nickName" placeholder="請輸入暱稱" show-word-limit maxlength="20"></el-input>
            </el-form-item>
          </el-form>
        </div>

        <!--      底部按鈕-->
        <div class="demo-drawer__footer" style="text-align: right">
          <el-button type="primary" @click="updateInfo" v-loading="loading.loadingUpdateInfo">確 定</el-button>
        </div>

      </div>
    </div>
    <!-- 管理員個人信息表單 End -->
  </div>
</template>

<script>
// import request from '@/utils/request'
import VueCookie from 'vue-cookie'
import AuthAPI from '../../api/auth'
import UserAPI from '../../api/user'
import SuperAdminAPI from '../../api/superAdmin'
import {Message} from 'element-ui'
// import {startLoading, endLoading} from '../../utils/loading'
export default {
  data () {
    return {
      // 數據中心
      // ROLE_USER,ROLE_ADMIN,ROLE_SUPER_ADMIN
      role: null,
      roleOptions: [{
        value: 'ROLE_SUPER_ADMIN',
        label: '超級管理員'
      }, {
        value: 'ROLE_ADMIN',
        label: '普通管理員'
      }, {
        value: 'ROLE_USER',
        label: '用戶'
      }],
      userInfo: {
        id: 1,
        studentNumber: '2021',
        password: '',
        name: '三哥',
        sex: 1,
        college: '計算機學院',
        grade: '17級軟件一斑级',
        phone: 13207920596,
        dormitory: '五棟404'
      },
      adminInfo: {
        id: 1,
        role: 0,
        username: 'admin',
        password: '',
        nickName: '超級管理員123'
      },
      loading: {
        loadingAuthInfo: false,
        loadingUpdateInfo: false
      }
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後數據獲取
  created () {
    this.getLoginUserInfo()
  },
  // 自定義方法
  methods: {
    async getLoginUserInfo () {
      // 先從緩存中獲取
      let authInfo = JSON.parse(VueCookie.get('authInfo'))
      if (authInfo === undefined || authInfo === null) {
        await AuthAPI.getAuthByToken().then(resp => {
          authInfo = resp.data
        }).finally(f => {
        })
      }
      this.role = authInfo.role
      this.userInfo = authInfo.userVO
      if (authInfo.userVO !== null) {
        this.userInfo = authInfo.userVO
        this.userInfo.password = null
      }

      if (authInfo.adminVO !== null) {
        this.adminInfo = authInfo.adminVO
        this.adminInfo.password = null
      }
    },
    updateInfo () {
      this.loading.loadingUpdateInfo = true
      if (this.role === 'ROLE_USER') {
        // 用戶信息修改
        UserAPI.update(this.userInfo).then(resp => {
          if (resp.code !== 200) {
            return
          }
          Message.success('修改成功，重新登錄才會刷新數據！')
        }).finally(f => {
          this.loading.loadingUpdateInfo = false
        })
      } else {
        // 管理員信息修改
        SuperAdminAPI.saveOrUpdate(this.adminInfo).then(resp => {
          if (resp.code !== 200) {
            return
          }
          Message.success('修改成功，重新登錄才會刷新數據！')
        }).finally(f => {
          this.loading.loadingUpdateInfo = false
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
