<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.categoryName" placeholder="類別名稱" clearable></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="findData">查詢</el-button>
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
          <el-table-column prop="categoryName" label="類別名稱"/>
          <el-table-column prop="createTime" label="創建時間" sortable/>
          <el-table-column
            fixed="right"
            label="操作"
            width="100" v-if="$roleRule(['ROLE_ADMIN'])">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                icon="el-icon-edit"
                @click="updateData(scope.$index, scope.row)">修改</el-button>
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

    <!--新增修改圖書表單 Start-->
    <el-dialog
      title="添加/修改圖書類別"
      :visible.sync="dialog.addDataDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.addDataDialog = false}">

      <!--      内容-->
      <div>
        <el-form ref="addDataRulesForm" label-position="right" :rules="addDataRulesForm" label-width="80px" :model="addData">
          <el-form-item label="類別名稱" prop="categoryName">
            <el-input v-model="addData.categoryName" placeholder="請輸入類別名稱"></el-input>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('addDataRulesForm')">取 消</el-button>
        <el-button
          v-loading="loading.loadingAddData"
          type="primary" @click="addDataInfo">確 定</el-button>
      </span>
    </el-dialog>
    <!--新增修改圖書表單 End-->

  </div>
</template>

<script>
// import request from '@/utils/request'
import BookCategoryAPI from '../../api/bookCategory'
import {Message} from 'element-ui'

export default {
  data () {
    return {
      // 數據中心
      queryData: {
        categoryName: null,

        total: 400,
        size: 10,
        current: 1,
        column: null
      },
      dialog: {
        addDataDialog: false
      },
      dataList: [],
      addData: {
        id: null,
        categoryName: null
      },
      addDataRulesForm: {
        categoryName: [{required: true, message: '請輸入類別名稱', trigger: 'blur'}]
      },
      loading: {
        loadingFindData: false,
        loadingAddData: false
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
      BookCategoryAPI.page(this.queryData).then(resp => {
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

    updateData (index, data) {
      this.addData.id = data.id
      this.addData.categoryName = data.categoryName
      this.dialog.addDataDialog = true
    },
    addDataInfo () {
      this.$refs['addDataRulesForm'].validate((valid) => {
        if (valid) {
          this.loading.loadingAddData = true
          BookCategoryAPI.saveOrUpdate(this.addData).then(resp => {
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
        categoryName: null
      }
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

</style>
