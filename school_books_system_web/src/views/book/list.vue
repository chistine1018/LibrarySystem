<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.bookNumber" placeholder="圖書編號" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.bookName" placeholder="圖書名稱" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.author" placeholder="作者" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-select
            v-model="queryData.categoryId"
            filterable
            clearable
            remote
            reserve-keyword
            placeholder="類別（可搜索）"
            :remote-method="findCategory"
            :loading="loading.loadingCategory">
            <el-option
              v-for="item in optionsCategory"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.publishingHouse" placeholder="出版社" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="findData">查詢</el-button>
          <el-button  @click="clearQueryData">置空</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查詢 End-->

    <!--功能 Start-->
    <div style="margin: 0 0 18px 0" v-if="$roleRule(['ROLE_ADMIN'])">
      <el-button type="primary" icon="el-icon-plus" @click="dialog.addDataDialog = true">新增</el-button>
    </div>
    <!--功能 End-->

    <!--列表 Start-->
    <div>
      <div style="max-height: 620px">
        <!--表格-->
        <el-table
          v-loading="loading.loadingFindData"
          border
          max-height="620"
          :data="dataList"
          @sort-change="dataSortChange"
          style="width: 100%">
          <el-table-column type="index" :index="1">
          </el-table-column>
          <el-table-column prop="id" label="id"/>
          <el-table-column prop="bookNumber" label="編號"/>
          <el-table-column label="封面" width="175">
            <template slot-scope="scope">
              <el-image
                style="width: 150px;height: 150px"
                :src="scope.row.coverUrl"
                :preview-src-list="[scope.row.coverUrl]">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="bookName" label="書名"/>
          <el-table-column prop="author" label="作者"/>
          <el-table-column prop="categoryName" label="類別"/>
          <el-table-column prop="price" label="價格"/>
          <el-table-column prop="publishingHouse" label="出版社"/>
          <el-table-column prop="publicationDate" label="出版時間" sortable width="120"/>
          <el-table-column prop="total" label="總數"/>
          <el-table-column prop="extantTotal" label="剩餘總數"/>
          <el-table-column label="簡介" width="400">
            <template slot-scope="scope">
              <div
                class="my-text-content"
                @click="openIntroContent(scope.row.intro)">
                {{scope.row.intro}}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="creator" label="創建人"/>
          <el-table-column prop="createTime" label="創建時間" sortable width="180"/>
          <el-table-column prop="modifier" label="修改人"/>
          <el-table-column prop="updateTime" label="修改時間" sortable width="180"/>
          <el-table-column
            fixed="right"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button  type="text"
                          @click="updateData(scope.$index, scope.row)" v-if="$roleRule(['ROLE_ADMIN'])">修改</el-button>
              <el-button  type="text"
                          @click="bookBorrow(scope.$index, scope.row)" v-if="$roleRule(['ROLE_USER'])">借閱</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="text-align: right;margin: 18px 0 0 0">
        <!--分頁-->
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryData.current"
          :page-sizes="[10, 20, 30, 40]"
          :page-size="10"
          layout="total, sizes, prev, pager, next, jumper"
          :total="queryData.total">
        </el-pagination>
      </div>
    </div>
    <!--列表 End-->

    <!--新增圖書表單 Start-->
    <el-drawer
      size="40%"
      title="添加圖書"
      close-on-press-escape
      :show-close="false"
      :wrapperClosable="false"
      custom-class="demo-drawer"
      :visible.sync="dialog.addDataDialog"
      direction="rtl">

      <div class="demo-drawer__content" style="padding: 20px 25px 0 15px;">

        <!--      内容-->
        <div>
          <el-form label-position="right" ref="addDataRulesForm" :rules="addDataRules" label-width="80px" :model="addData">
            <el-form-item label="封面圖" prop="coverUrl">
              <el-upload
                v-loading="loading.loadingImgFileUpload"
                class="avatar-uploader"
                :action="imgFileUploadUrl"
                :headers="imgFileUploadHeaders"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :on-error="handleAvatarError"
                :before-upload="beforeAvatarUpload">
                <img v-if="addData.coverUrl" :src="addData.coverUrl" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>

            </el-form-item>
            <el-form-item label="編號" prop="bookNumber">
              <el-input v-model="addData.bookNumber" placeholder="請輸入編號"></el-input>
            </el-form-item>
            <el-form-item label="書名" prop="bookName">
              <el-input v-model="addData.bookName" placeholder="請輸入書名"></el-input>
            </el-form-item>
            <el-form-item label="作者" prop="author">
              <el-input v-model="addData.author" placeholder="請輸入作者"></el-input>
            </el-form-item>
            <el-form-item label="類別" prop="categoryId">
              <el-select
                v-model="addData.categoryId"
                filterable
                clearable
                remote
                reserve-keyword
                placeholder="搜索類別"
                :remote-method="findCategory"
                :loading="loading.loadingCategory">
                <el-option
                  v-for="item in optionsCategory"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="價格">
              <el-input-number v-model="addData.price" :precision="2" :min="0" :step="1" :max="10000"/>
            </el-form-item>
            <el-form-item label="出版社" prop="publishingHouse">
              <el-input v-model="addData.publishingHouse" placeholder="請輸入出版社"></el-input>
            </el-form-item>
            <el-form-item label="出版日期" prop="publicationDate">
              <el-date-picker
                v-model="addData.publicationDate"
                type="date"
                placeholder="請輸入出版日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"/>
            </el-form-item>
            <el-form-item label="總數">
              <el-input-number v-model="addData.total" controls-position="right"  :min="1" :max="10000"/>
            </el-form-item>
            <el-form-item label="簡介" prop="intro">
              <el-input
                type="textarea"
                show-word-limit
                maxlength="500"
                :autosize="{ minRows: 2, maxRows: 4 }"
                placeholder="請輸入簡介"
                v-model="addData.intro"/>
            </el-form-item>
          </el-form>
        </div>

        <!--      底部按鈕-->
        <div class="demo-drawer__footer" style="text-align: right; padding-bottom: 35px">
          <el-button @click="resetForm('addDataRulesForm')">取 消</el-button>
          <el-button type="primary" @click="addBook">確 定</el-button>
        </div>

      </div>

    </el-drawer>
    <!--新增圖書表單 End-->

    <!--借閱圖書表單 Start-->
    <el-dialog
      title="借閱圖書"
      :visible.sync="dialog.bookBorrowDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.bookBorrowDialog = false}">

      <!--      内容-->
      <div>
        <el-form label-position="right" label-width="80px" :model="bookBorrowData">
          <el-form-item label="借閱書籍">
            <span>{{bookBorrowData.bookName}}</span>
          </el-form-item>
          <el-form-item label="借閱數量">
            <el-input-number v-model="bookBorrowData.borrowTotal" controls-position="right"  :min="1" :max="bookBorrowData.total"/>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.bookBorrowDialog = false">取 消</el-button>
        <el-button type="primary" v-loading="loading.loadingBookBorrowData" @click="doBookBorrow">確 定</el-button>
      </span>
    </el-dialog>
    <!--借閱圖書表單 End-->

    <!--圖書簡介詳情 Start-->
    <el-dialog
      title="圖書簡介"
      :visible.sync="dialog.introDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.introDialog = false}">
      <div style="white-space: pre-wrap;word-break: break-all;">{{introContent}}</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog.introDialog = false">確 定</el-button>
      </span>
    </el-dialog>
    <!--圖書簡介詳情 End-->
  </div>
</template>

<script>
// import request from '@/utils/request'
import BookAPI from '../../api/book'
import VueCookie from 'vue-cookie'
import {Message} from 'element-ui'
import BookCategoryAPI from '../../api/bookCategory'
import BookBorrowAPI from '../../api/bookBorrow'

export default {
  data () {
    return {
      // 數據中心
      // imgFileUploadUrl: 'http://localhost:9292/fileUpload/imgUpload',
      imgFileUploadUrl: '/api/fileUpload/imgUpload',
      imgFileUploadHeaders: {},
      queryData: {
        bookNumber: null,
        bookName: null,
        author: null,
        categoryId: null,
        publishingHouse: null,

        total: 400,
        size: 10,
        current: 1,
        column: null
      },
      dialog: {
        addDataDialog: false,
        bookBorrowDialog: false,
        introDialog: false
      },
      optionsCategory: [],
      loading: {
        loadingImgFileUpload: false,
        loadingFindData: false,
        loadingAddData: false,
        loadingBookBorrowData: false,
        loadingCategory: false
      },
      dataList: [],
      addData: {
        id: null,
        coverUrl: null,
        bookNumber: null,
        bookName: null,
        author: null,
        categoryId: null,
        price: 0,
        publishingHouse: null,
        publicationDate: null,
        total: 1,
        intro: null
      },
      addDataRules: {
        coverUrl: [{required: true, message: '封面不為空', trigger: 'blur'}],
        bookNumber: [{required: true, message: '請輸入編號', trigger: 'blur'}],
        bookName: [{required: true, message: '請輸入書名', trigger: 'blur'}],
        author: [{required: true, message: '請輸入作者', trigger: 'blur'}],
        categoryId: [{required: true, message: '請選擇分類', trigger: 'blur'}],
        price: [{required: true, message: '請輸入價格', trigger: 'blur'}],
        publishingHouse: [{required: true, message: '請輸入出版社', trigger: 'blur'}],
        publicationDate: [{required: true, message: '請輸入出版日期', trigger: 'blur'}],
        total: [{required: true, message: '請輸入總數', trigger: 'blur'}],
        intro: [{required: true, message: '請輸入簡介', trigger: 'blur'}]
      },

      bookBorrowData: {
        bookId: null,
        total: 5,
        bookName: 'Java從入門到精通',
        borrowTotal: 1
      },
      introContent: null
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後獲取數據
  created () {
    this.findData()
    this.findCategory()
    this.imgFileUploadHeaders = {
      'Authorization': VueCookie.get('Authorization')
    }
  },
  // 自定義方法
  methods: {
    findData () {
      this.loading.loadingFindData = true
      BookAPI.page(this.queryData).then(resp => {
        if (resp.code !== 200) {
          return
        }
        this.dataList = resp.data.records
        this.queryData.total = resp.data.total
        this.queryData.size = resp.data.size
        this.queryData.current = resp.data.current
      }).finally(f => {
        this.loading.loadingFindData = false
      })
    },
    clearQueryData () {
      this.queryData.bookNumber = null
      this.queryData.bookName = null
      this.queryData.author = null
      this.queryData.categoryId = null
      this.queryData.publishingHouse = null
    },
    addBook () {
      // console.log('this.addData', this.addData)
      this.$refs['addDataRulesForm'].validate((valid) => {
        if (valid) {
          this.loading.loadingAddData = true
          BookAPI.saveOrUpdate(this.addData).then(resp => {
            if (resp.code !== 200) {
              return
            }
            Message.success('添加成功！')
            this.resetForm('addDataRulesForm')
          }).finally(f => {
            this.loading.loadingAddData = false
          })
        } else {

        }
      })
    },
    resetForm (formName) {
      this.findData()
      this.dialog.addDataDialog = false
      this.$refs[formName].resetFields()
      this.addData = {
        id: null,
        coverUrl: null,
        bookNumber: null,
        bookName: null,
        author: null,
        categoryId: null,
        price: null,
        publishingHouse: null,
        publicationDate: null,
        total: null,
        intro: null
      }
    },
    updateData (index, data) {
      console.log('removeData', data)
      let exist = false
      this.optionsCategory.forEach(item => {
        if (item.id === data.categoryId) {
          exist = true
        }
      })
      if (!exist) {
        this.optionsCategory.push({'id': data.categoryId, 'categoryName': data.categoryName})
      }

      this.addData = data
      this.dialog.addDataDialog = true
    },

    bookBorrow (index, data) {
      this.bookBorrowData.bookId = data.id
      this.bookBorrowData.total = data.total
      this.bookBorrowData.bookName = data.bookName
      this.bookBorrowData.borrowTotal = 1
      this.dialog.bookBorrowDialog = true
    },
    doBookBorrow () {
      this.loading.loadingBookBorrowData = true
      BookBorrowAPI.bookBorrow(this.bookBorrowData).then(resp => {
        if (resp.code !== 200) {
          return
        }
        Message.success('借閱成功！')
        this.findData()
        this.dialog.bookBorrowDialog = false
      }).finally(f => {
        this.loading.loadingBookBorrowData = false
      })
    },
    findCategory (categoryName) {
      this.loading.loadingCategory = true
      BookCategoryAPI.page({'categoryName': categoryName, 'size': 10}).then(resp => {
        if (resp.code !== 200) {
          return
        }
        this.optionsCategory = resp.data.records
      }).finally(f => {
        this.loading.loadingCategory = false
      })
    },

    openIntroContent (intro) {
      this.introContent = intro
      this.dialog.introDialog = true
    },

    dataSortChange (sortInfo) {
      // prop : 字段，order ：ascending上，descending下
      if (sortInfo.order === 'ascending') {
        this.queryData.column = '-' + sortInfo.prop
      } else {
        this.queryData.column = '+' + sortInfo.prop
      }
      this.findData()
    },
    handleSizeChange (val) {
      this.queryData.size = val
      this.queryData.current = 1
      this.findData()
    },
    handleCurrentChange (val) {
      // 改變頁碼
      console.log('handleCurrentChange', val)
      this.queryData.current = val
      this.findData()
    },

    handleAvatarSuccess (response, file, fileList) {
      if (response.code !== 200) {
        Message.error(response.message + ':' + response.exception)
      } else {
        this.addData.coverUrl = response.data
        Message.success('上傳成功！')
      }
      this.loading.loadingImgFileUpload = false
    },
    handleAvatarError (error, file, fileList) {
      Message.error(error.message)
      this.loading.loadingImgFileUpload = false
    },
    beforeAvatarUpload (file) {
      this.loading.loadingImgFileUpload = true
    }
  }
}
</script>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.my-text-content {
  display: -webkit-box;
  overflow: hidden;
  text-overflow: ellipsis;
  word-break: break-all;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  cursor: pointer;
  color: cornflowerblue;
}
</style>
