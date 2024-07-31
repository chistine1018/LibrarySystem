<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.studentNumber" placeholder="學號" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.name" placeholder="學生姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.bookNumber" placeholder="圖書編號" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.bookName" placeholder="圖書名稱" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-date-picker
            clearable
            v-model="timeStr"
            type="daterange"
            align="right"
            unlink-panels
            range-separator="至"
            start-placeholder="開始日期"
            end-placeholder="結束日期"
            :picker-options="pickerOptions"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="findData">查詢</el-button>
          <el-button  @click="clearQueryData">置空</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--查詢 End-->

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
          <el-table-column prop="bookNumber" label="圖書編號"/>
          <el-table-column label="封面" width="175">
            <template slot-scope="scope">
              <el-image
                style="width: 150px"
                :src="scope.row.coverUrl"
                :preview-src-list="[scope.row.coverUrl]">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="bookName" label="書名"/>
          <el-table-column prop="studentNumber" label="學號"/>
          <el-table-column prop="name" label="學生姓名"/>
          <el-table-column prop="borrowCount" label="借閱數量"/>
          <el-table-column prop="returnCount" label="歸還數量"/>
          <el-table-column prop="createTime" label="創建時間" sortable width="180"/>
          <el-table-column prop="updateTime" label="修改時間" sortable width="180"/>
          <el-table-column
            fixed="right"
            label="操作"
            width="120">
            <template slot-scope="scope">
              <el-button  type="text"  @click="returnBook(scope.$index, scope.row)">還書</el-button>
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

    <!--歸還圖書列表 Start-->
    <el-dialog
      title="歸還圖書"
      :visible.sync="dialog.returnBookDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.returnBookDialog = false}">

      <!--      内容-->
      <div>
        <el-form label-position="right" label-width="80px" :model="returnBookData">
          <el-form-item label="歸還者">
            <span>{{returnBookData.name}}</span>
          </el-form-item>
          <el-form-item label="歸還書籍">
            <span>{{returnBookData.bookName}}</span>
          </el-form-item>
          <el-form-item label="歸還數量">
            <el-input-number v-model="returnBookData.returnTotal" controls-position="right"  :min="1" :max="returnBookData.total"/>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialog.returnBookDialog = false">取 消</el-button>
        <el-button type="primary" v-loading="loading.loadingReturnBookData" @click="doReturnBook">確 定</el-button>
      </span>
    </el-dialog>
    <!--歸還圖書列表 End-->

  </div>
</template>

<script>
// import request from '@/utils/request'
import BookBorrowAPI from '../../api/bookBorrow'
import UserReturnBookAPI from '../../api/userReturnBook'
import {Message} from 'element-ui'

export default {
  data () {
    return {
      // 數據中心
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一個月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三個月',
          onClick (picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      loading: {
        loadingFindData: false,
        loadingReturnBookData: false
      },
      timeStr: null,
      queryData: {
        studentNumber: null,
        name: null,
        bookNumber: null,
        bookName: null,
        startTime: null,
        endTime: null,

        total: 0,
        size: 10,
        current: 1,
        column: null
      },
      dataList: [],
      dialog: {
        returnBookDialog: false
      },
      returnBookData: {
        id: null,
        name: null,
        bookName: null,
        returnTotal: null,
        total: null
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
      if (this.timeStr !== null) {
        this.queryData.startTime = this.timeStr[0]
        this.queryData.endTime = this.timeStr[1]
      } else {
        this.queryData.startTime = null
        this.queryData.endTime = null
      }
      this.loading.loadingFindData = true
      BookBorrowAPI.page(this.queryData).then(resp => {
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
      this.queryData.studentNumber = null
      this.queryData.name = null
      this.queryData.bookNumber = null
      this.queryData.bookName = null
      this.queryData.startTime = null
      this.queryData.endTime = null
      this.timeStr = null
    },

    doReturnBook () {
      this.loading.loadingReturnBookData = true
      UserReturnBookAPI.userReturnBook({
        'bookBorrowInfoId': this.returnBookData.id,
        'count': this.returnBookData.returnTotal
      })
        .then(resp => {
          if (resp.code !== 200) {
            return
          }
          Message.success('歸還成功！')
          this.findData()
          this.dialog.returnBookDialog = false
        }).finally(f => {
          this.loading.loadingReturnBookData = false
        })
    },

    returnBook (index, row) {
      this.returnBookData.id = row.id
      this.returnBookData.name = row.name
      this.returnBookData.bookName = row.bookName
      this.returnBookData.returnTotal = 1
      this.returnBookData.total = row.borrowCount - row.returnCount
      this.dialog.returnBookDialog = true
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
      this.queryData.current = val
      this.findData()
    }
  }
}
</script>

<style scoped>

</style>
