<template>
  <div class="app-container">
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
          border
          max-height="620"
          :data="dataList"
          @sort-change="dataSortChange"
          style="width: 100%">
          <el-table-column type="index" :index="1">
          </el-table-column>
          <el-table-column prop="id" label="id"/>
          <el-table-column label="輪播圖" width="400">
            <template slot-scope="scope">
              <el-image
                style="width: 200px"
                :src="scope.row.imgUrl"
                :preview-src-list="[scope.row.imgUrl]">
              </el-image>
            </template>
          </el-table-column>
          <el-table-column prop="weight" label="權重(越小權重越高)" sortable/>
          <el-table-column prop="creator" label="創建人"/>
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

    <!--添加輪播圖表單 Start-->
    <el-dialog
      title="添加輪播圖"
      :visible.sync="dialog.addDataDialog"
      width="30%"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
      :before-close="() => {dialog.addDataDialog = false}">

      <!--      内容-->
      <div>
        <el-form ref="addDataRulesForm"  label-position="right" label-width="80px" :model="addData" :rules="addDataRules">
          <el-form-item label="輪播圖" prop="imgUrl">
            <el-upload
              v-loading="loading.loadingImgFileUpload"
              class="avatar-uploader"
              :action="imgFileUploadUrl"
              :headers="imgFileUploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :on-error="handleAvatarError"
              :before-upload="beforeAvatarUpload">
              <img v-if="addData.imgUrl" :src="addData.imgUrl" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="權重">
            <el-input-number v-model="addData.weight" controls-position="right"  :min="1"/>
          </el-form-item>
        </el-form>
      </div>

      <!--      底部按鈕-->
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm('addDataRulesForm')">取 消</el-button>
        <el-button type="primary" v-loading="loading.loadingAddData" @click="addCarouseMap">確 定</el-button>
      </span>
    </el-dialog>
    <!--添加輪播圖表單 End-->
  </div>
</template>

<script>
// import request from '@/utils/request'
import VueCookie from 'vue-cookie'
import {Message} from 'element-ui'
import CarouselMapAPI from '../../api/carouselMap'

export default {
  data () {
    return {
      // 數據中心

      imgFileUploadUrl: 'http://localhost:9292/fileUpload/imgUpload',
      imgFileUploadHeaders: {},

      queryData: {
        total: 0,
        size: 10,
        current: 1,
        column: null
      },
      dialog: {
        addDataDialog: false
      },
      loading: {
        loadingFindData: false,
        loadingAddData: false,
        loadingImgFileUpload: false,
        loadingDeleteData: false
      },

      addData: {
        imgUrl: null,
        weight: 10
      },
      addDataRules: {
        imgUrl: [{required: true, message: '輪播圖不為空', trigger: 'blur'}]
      },
      dataList: []
    }
  },
  // 組件
  computed: {},
  // 頁面渲染成功後獲取數據
  created () {
    this.findData()
    this.imgFileUploadHeaders = {
      'Authorization': VueCookie.get('Authorization')
    }
  },
  // 自定義方法
  methods: {
    findData () {
      this.loading.loadingFindData = true
      CarouselMapAPI.page(this.queryData).then(resp => {
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

    removeData (index, row) {
      this.loading.loadingDeleteData = true
      CarouselMapAPI.delete({'id': row.id}).then(resp => {
        if (resp.code !== 200) {
          return
        }
        Message.success('刪除成功！')
        this.findData()
      }).finally(f => {
        this.loading.loadingDeleteData = false
      })
    },

    addCarouseMap () {
      // console.log('this.addData', this.addData)
      this.$refs['addDataRulesForm'].validate((valid) => {
        if (valid) {
          this.loading.loadingAddData = true
          CarouselMapAPI.save(this.addData).then(resp => {
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
        imgUrl: null,
        weight: 10
      }
    },

    updateData (data) {
      console.log('removeData', data)
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
    },

    handleAvatarSuccess (response, file, fileList) {
      if (response.code !== 200) {
        Message.error(response.message + ':' + response.exception)
      } else {
        this.addData.imgUrl = response.data
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

<style scoped>
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
</style>
