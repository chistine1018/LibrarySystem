<template>
  <div class="app-container">
    <!--查詢 Start-->
    <div>
      <el-form :inline="true" :model="queryData" class="demo-form-inline">
        <el-form-item label="">
          <el-input v-model="queryData.studentNumber" placeholder="學號" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.name" placeholder="姓名" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.sex" placeholder="性别" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.college" placeholder="學院" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.grade" placeholder="班级" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.phone" placeholder="電話" clearable></el-input>
        </el-form-item>
        <el-form-item label="">
          <el-input v-model="queryData.dormitory" placeholder="宿舍" clearable></el-input>
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
          <el-table-column prop="studentNumber" label="學號"/>
          <el-table-column prop="name" label="姓名"/>
          <el-table-column prop="sex" label="性别"/>
          <el-table-column prop="college" label="學院"/>
          <el-table-column prop="grade" label="班级"/>
          <el-table-column prop="phone" label="電話"/>
          <el-table-column prop="dormitory" label="宿舍"/>
          <el-table-column prop="createTime" label="創建時間" sortable width="180"/>
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
  </div>
</template>

<script>
// import request from '@/utils/request'
import UserAPI from '../../api/user'
import {Message} from 'element-ui'
export default {
  data () {
    return {
      // 數據中心
      queryData: {
        studentNumber: null,
        name: null,
        sex: null,
        college: null,
        grade: null,
        phone: null,
        dormitory: null,

        total: 400,
        size: 10,
        current: 1,
        column: null
      },
      dataList: [],
      loading: {
        loadingFindData: false,
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
      UserAPI.page(this.queryData).then(resp => {
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
      this.queryData.sex = null
      this.queryData.college = null
      this.queryData.grade = null
      this.queryData.phone = null
      this.queryData.dormitory = null
    },
    removeData (index, data) {
      this.loading.loadingDeleteData = true
      UserAPI.delete({'id': data.id}).then(resp => {
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

</style>
