<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.username" placeholder="帳號" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.nickName" placeholder="暱稱" clearable></el-input>
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
          <el-table-column prop="username" label="帳號"/>
          <el-table-column prop="nickName" label="暱稱" />
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
                <el-button v-loading="loading.loadingDeleteData" size="mini" slot="reference" type="danger">删除</el-button>
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

    <!--添加帳號表單 Start-->
    <el-dialog
      title="添加管理員帳號"
      :visible.sync="dialog.addDataDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.addDataDialog = false}">

      <!--      内容-->
      <div>
        <el-form label-position="right" ref="addDataRulesForm" :rules="addDataRules" label-width="60px" :model="addData">
          <el-form-item label="帳號" prop="username">
            <el-input v-model="addData.username" placeholder="請輸入帳號" show-word-limit maxlength="20"></el-input>
          </el-form-item>
          <el-form-item label="密碼" prop="password">
            <el-input v-model="addData.password"
                      placeholder="請輸入密碼"
                      type="password"
                      show-password
            ></el-input>
          </el-form-item>
          <el-form-item label="暱稱" prop="nickName">
            <el-input v-model="addData.nickName" placeholder="請輸入暱稱" show-word-limit maxlength="20"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('addDataRulesForm')">取 消</el-button>
        <el-button type="primary" @click="addAdmin">確 定</el-button>
      </span>
    </el-dialog>
    <!--添加帳號表單 End-->
  </div>
</template>

<script>
// import request from '@/utils/request'
import SuperAdminAPI from '../../api/superAdmin'
import {Message} from 'element-ui'
export default {
  data () {
    return {
      // 數據中心
      queryData: {
        username: null,
        nickName: null,

        total: 400,
        size: 10,
        current: 1,
        column: null
      },
      dialog: {
        addDataDialog: false
      },
      dataList: [

      ],
      addData: {
        username: null,
        password: null,
        nickName: null
      },
      addDataRules: {
        username: [{ required: true, message: '請輸入帳號', trigger: 'blur' }],
        password: [{ required: true, message: '請輸入密碼', trigger: 'blur' }],
        nickName: [{ required: true, message: '請輸入暱稱', trigger: 'blur' }]
      },
      loading: {
        loadingFindData: false,
        loadingAddData: false,
        loadingDeleteData: false
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
      SuperAdminAPI.page(this.queryData).then(resp => {
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
      this.queryData.username = null
      this.queryData.nickName = null
    },
    removeData (index, data) {
      this.loading.loadingDeleteData = true
      SuperAdminAPI.delete({'id': data.id}).then(resp => {
        if (resp.code !== 200) {
          return
        }
        Message.success('刪除成功！')
        this.findData()
      }).finally(f => {
        this.loading.loadingDeleteData = false
      })
    },
    addAdmin () {
      this.$refs['addDataRulesForm'].validate((valid) => {
        if (valid) {
          this.loading.loadingAddData = true
          SuperAdminAPI.saveOrUpdate(this.addData).then(resp => {
            if (resp.code !== 200) {
              return
            }
            Message.success('添加成功！')
            this.resetForm('addDataRulesForm')
            this.findData()
          }).finally(f => {
            this.loading.loadingAddData = false
          })
        } else {

        }
      })
    },
    resetForm (formName) {
      this.dialog.addDataDialog = false
      this.$refs[formName].resetFields()
      this.addData = {
        username: null,
        password: null,
        nickName: null
      }
    },
    dataSortChange (sortInfo) {
      console.log(sortInfo)
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
.el-divider--horizontal {
  display: block;
  height: 1px;
  width: 100%;
  margin: 0 0 18px 0;
}
</style>
