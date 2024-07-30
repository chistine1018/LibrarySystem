<template>
  <div class="app-container">
    <!-- 添加custom background Start -->
    <div class="login-backdrop">
      <div style="margin-top: 220px">

      </div>
      <div style="width: 340px;
            text-align: center;
            background-color: white;
            padding: 30px;">
        <div>
          <h1>學校圖書管理系統</h1>
        </div>
        <!-- 登入表單 Start-->
        <div>
          <el-form ref="ruleForm" label-position="" :rules="loginRules" :model="loginForm">
            <el-form-item label="" prop="username">
              <el-input
                placeholder="請輸入帳號/學號"
                prefix-icon="el-icon-user"
                v-model="loginForm.username"/>
            </el-form-item>
            <el-form-item label="" prop="password">
              <el-input v-model="loginForm.password"
                        placeholder="請輸入密碼"
                        prefix-icon="el-icon-goods"
                        type="password"
                        show-password
              />
            </el-form-item>
            <el-form-item label="" prop="role">
              <el-select v-model="loginForm.role" placeholder="請選擇角色"
                         style="width: 100%"
              >
                <el-option
                  v-for="item in roleOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button type="primary"
                     v-loading="loading.loginLoading"
                     style="width: 100%"
                     @click="loginHandler"
          >登 錄
          </el-button>
          <div style="text-align: right" v-if="loginForm.role === 'ROLE_USER'">
            <el-button type="text" size="medium" @click="() =>{this.dialog.registerUserDialog = true}">註 冊</el-button>
          </div>
        </div>
        <!-- 登入表單 End-->
      </div>
    </div>
    <!-- 添加custom background End -->

    <!-- 註冊表單 Start -->
    <el-dialog
      title="用戶註冊"
      :visible.sync="dialog.registerUserDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.registerUserDialog = false}">

      <!--      内容-->
      <div>
        <el-form ref="ruleRegisterForm" :rules="registerRules" label-position="right" label-width="80px"
                 :model="userInfo">
          <el-form-item label="學號" prop="studentNumber">
            <el-input v-model="userInfo.studentNumber" placeholder="請輸入學號" show-word-limit
                      maxlength="32"></el-input>
          </el-form-item>
          <el-form-item label="密碼" prop="password">
            <el-input v-model="userInfo.password"
                      placeholder="請輸入密碼"
                      type="password"
                      show-password/>
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="userInfo.name" placeholder="請輸入姓名" show-word-limit maxlength="20"></el-input>
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userInfo.sex">
              <el-radio :label="1">男</el-radio>
              <el-radio :label="0">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="學院" prop="college">
            <el-input v-model="userInfo.college" placeholder="請輸入學院" show-word-limit maxlength="50"></el-input>
          </el-form-item>
          <el-form-item label="班级" prop="grade">
            <el-input v-model="userInfo.grade" placeholder="請輸入班級" show-word-limit maxlength="20"></el-input>
          </el-form-item>
          <el-form-item label="電話" prop="phone">
            <el-input v-model="userInfo.phone" placeholder="請輸入電話" show-word-limit maxlength="11"></el-input>
          </el-form-item>
          <el-form-item label="宿舍" prop="dormitory">
            <el-input v-model="userInfo.dormitory" placeholder="請輸入宿舍" show-word-limit maxlength="50"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <div class="demo-drawer__footer" style="text-align: right">
        <el-button @click="resetForm('ruleRegisterForm')">取 消</el-button>
        <el-button type="primary" v-loading="loading.registerLoading" @click="register()">確 定</el-button>
      </div>

    </el-dialog>
    <!-- 註冊表單 End -->
  </div>
</template>

<script>
// import request from '@/utils/request'
// import {Message} from 'element-ui'

export default {
  data () {
    return {
      // 數據中心
      loginForm: {
        username: null,
        password: null,
        role: 'ROLE_USER'
      },
      loading: {
        loginLoading: false,
        registerLoading: false
      },
      loginRules: {
        username: [{required: true, message: '請輸入帳號', trigger: 'blur'}],
        password: [{required: true, message: '請輸入密碼', trigger: 'blur'}]
      },
      registerRules: {
        studentNumber: [{required: true, message: '請輸入學號', trigger: 'blur'}],
        password: [{required: true, message: '請輸入密碼', trigger: 'blur'}],
        name: [{required: true, message: '請輸入姓名', trigger: 'blur'}],
        college: [{required: true, message: '請輸入學院', trigger: 'blur'}],
        grade: [{required: true, message: '請輸入班級', trigger: 'blur'}],
        phone: [{required: true, message: '請輸入電話', trigger: 'blur'}],
        dormitory: [{required: true, message: '請輸入宿舍', trigger: 'blur'}]
      },
      dialog: {
        registerUserDialog: false
      },
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
        id: null,
        studentNumber: null,
        password: null,
        name: null,
        sex: 1,
        college: null,
        grade: null,
        phone: null,
        dormitory: null
      }
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後獲取數據
  created () {

  },
  // 自定義方法
  methods: {
    loginHandler () {
      console.log('this.loginForm', this.loginForm)
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.loading.loginLoading = true
        } else {
          return false
        }
      })
    },

    register () {
      console.log('this.userInfo', this.userInfo)
      this.$refs['ruleRegisterForm'].validate((valid) => {
        if (valid) {
          this.loading.registerLoading = true
        } else {

        }
      })
    },

    resetForm (formName) {
      this.dialog.registerUserDialog = false
      this.$refs[formName].resetFields()
      this.userInfo = {
        id: null,
        studentNumber: null,
        password: null,
        name: null,
        sex: 1,
        college: null,
        grade: null,
        phone: null,
        dormitory: null
      }
    }
  }
}
</script>

<style scoped>
.container {
  display: flex; /* 設置容器為 Flexbox */
  justify-content: center; /* 水平居中對齊子項目 */
  align-items: center; /* 垂直居中對齊子項目 */
}

.login-backdrop {
  width: 100%;
  height: 100%;
  position: absolute;
  text-align: -webkit-center;
  margin: -8px;
  background-image: url("../../assets/beijing.jpg");
}
</style>
