<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.title" placeholder="公告標題" clearable></el-input>
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

    <!--功能 Start-->
    <div style="margin: 0 0 18px 0">
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
          <el-table-column prop="title" label="標題"/>
          <el-table-column label="内容" width="400">
            <template slot-scope="scope">
              <div
                class="my-text-content"
                @click="openContent(scope.row)">
                {{scope.row.content}}
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="weight" label="優先級(越小優先級越高)" sortable/>
          <el-table-column prop="createTime" label="創建時間" sortable/>
          <el-table-column
            fixed="right"
            label="操作"
            width="100">
            <template slot-scope="scope">
              <el-popconfirm
                confirm-button-text='確定'
                cancel-button-text='再想想'
                icon="el-icon-info"
                icon-color="red"
                title="確定刪除該條數據嗎？"
                @confirm="removeData(scope.$index, scope.row)"
              >
                <el-button v-loading="loading.loadingDeleteData" size="mini" slot="reference" type="danger">刪除</el-button>
              </el-popconfirm>
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

    <!--新增公告表單 Start-->
    <el-dialog
      title="添加公告"
      :visible.sync="dialog.addDataDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.addDataDialog = false}">

      <!--      内容-->
      <div>
        <el-form ref="addDataRulesForm" label-position="right" label-width="60px" :model="addData" :rules="addDataRules">
          <el-form-item label="標題" prop="title">
            <el-input v-model="addData.title"
                      show-word-limit
                      maxlength="50"
                      placeholder="請輸入標題"></el-input>
          </el-form-item>
          <el-form-item label="内容" prop="content">
            <el-input
              type="textarea"
              show-word-limit
              maxlength="1000"
              :autosize="{ minRows: 3, maxRows: 10 }"
              placeholder="請輸入簡介"
              v-model="addData.content"/>
          </el-form-item>
          <el-form-item label="權重">
            <el-input-number v-model="addData.weight" controls-position="right"  :min="1"/>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('addDataRulesForm')">取 消</el-button>
        <el-button type="primary" @click="addAnnouncement">確 定</el-button>
      </span>
    </el-dialog>
    <!--新增公告表單 End-->

    <!--公告詳情 Start-->
    <el-dialog
      title="公告内容"
      :visible.sync="dialog.contentDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.contentDialog = false}">
      <div style="white-space: pre-wrap;word-break: break-all;">{{announcementContent}}</div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog.contentDialog = false">確 定</el-button>
      </span>
    </el-dialog>
    <!--公告詳情 End-->

  </div>
</template>

<script>
// import request from '@/utils/request'
import AnnouncementAPI from '../../api/announcement'
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
      timeStr: null,
      queryData: {
        title: null,
        startTime: null,
        endTime: null,

        total: 0,
        size: 10,
        current: 1,
        column: null
      },
      dataList: [],
      dialog: {
        addDataDialog: false,
        contentDialog: false
      },
      addData: {
        title: null,
        content: null,
        weight: 10
      },
      addDataRules: {
        title: [{required: true, message: '標題不為空', trigger: 'blur'}],
        content: [{required: true, message: '内容不為空', trigger: 'blur'}]
      },
      loading: {
        loadingFindData: false,
        loadingAddData: false,
        loadingDeleteData: false
      },
      announcementContent: null
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
      AnnouncementAPI.page(this.queryData).then(resp => {
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
      this.queryData.title = null
      this.queryData.startTime = null
      this.queryData.endTime = null
      this.timeStr = null
    },

    addAnnouncement () {
      // console.log('this.addData', this.addData)
      this.$refs['addDataRulesForm'].validate((valid) => {
        if (valid) {
          this.loading.loadingAddData = true
          AnnouncementAPI.save(this.addData).then(resp => {
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
        title: null,
        content: null,
        weight: 10
      }
    },

    openContent (row) {
      console.log(row.content)
      this.announcementContent = row.content
      this.dialog.contentDialog = true
    },

    removeData (index, row) {
      this.loading.loadingDeleteData = true
      AnnouncementAPI.delete({'id': row.id}).then(resp => {
        if (resp.code !== 200) {
          return
        }
        Message.success('刪除成功！')
        this.findData()
      }).finally(f => {
        this.loading.loadingDeleteData = false
      })
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
      this.queryData.current = this.queryData.current + 1
      this.findData()
    }
  }
}
</script>

<style scoped>
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
