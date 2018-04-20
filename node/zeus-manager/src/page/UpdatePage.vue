<template>
  <el-form ref="form" :model="form" label-width="100px" v-loading.fullscreen.lock="uploadProgressFlag"
           element-loading-text="上传中...."
           element-loading-spinner="el-icon-loading"
           element-loading-background="rgba(0, 0, 0, 0.8)">
    <el-form-item label="上传包">
      <el-upload
        name="file"
        :drag="false"
        accept=".apk,.ipa"
        action="http://localhost:8081/upload/single"
        :multiple="false"
        :show-file-list="false"
        :before-upload="beforeUpload"
        :on-error="uploadError"
        :on-success="uploadSuccess">
        <span class="el-upload__text" style="color: #606266;margin-right: 20px">{{uploadPathInfo}}</span>
        <el-button size="small" type="primary">点击上传</el-button>
      </el-upload>
    </el-form-item>
    <el-form-item label="App名称">
      <el-input v-model="form.name" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="客户端类型">
      <el-input v-model="form.type" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="版本号">
      <el-input v-model="form.version" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="发布描述">
      <el-input v-model="form.desc" type="textarea"></el-input>
    </el-form-item>
    <el-form-item label="是否强制更新">
      <el-switch v-model="form.forceFlag"></el-switch>
    </el-form-item>
    <el-form-item label="发布时间">
      <el-date-picker
        v-model="timeValues"
        type="datetimerange"
        :picker-options="pickerOptions2"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        align="right">
      </el-date-picker>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即发布</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
  import moment from 'moment';
  export default {
    data() {
      return {
        form: {
          name: '',
          type: '',
          version: '',
          desc: '',
          forceFlag: false,
          startDate: '',
          endDate: '',
        },
        uploadProgressFlag: false,
        uploadPathInfo: '',
        pickerOptions2: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近半年',
            onClick(picker) {
              const start = new Date();
              const end = new Date();
              end.setTime(end.getTime() + 3600 * 1000 * 24 * 180);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
        timeValues: []
      }
    },
    created() {
    },
    watch: {
      timeValues: function (val) {
        this.form.startDate=moment(val[0]).format('YYYY-MM-DD HHmmss');
        this.form.endDate=moment(val[1]).format('YYYY-MM-DD HHmmss');
      }
    },
    methods: {
      onSubmit() {
        this.$http.post('checkUpdate', this.form)
          .then(value => console.log(value))
          .catch(err => console.log(err));
      },
      beforeUpload(file) {
        var nameLen = file.name.length;
        var suffix = file.name.substring(nameLen - 4);
        if (suffix == '.apk' || suffix == '.ipa') {
          this.uploadProgressFlag = true;
          return true;
        } else {
          this.$message.error('上传文件必须为安卓或苹果安装包');
          return false;
        }
      },
      uploadSuccess(response, file, fileList) {
        this.uploadProgressFlag = false;
        if (response.code == 10000) {
          this.form.name = response.name;
          this.form.type = response.type;
          this.form.version = response.version;
          this.uploadPathInfo = file.name + ' 上传成功';
          this.$message(this.uploadPathInfo);
          console.log(response)
        } else {
          this.$message(response.message);
        }
      },
      uploadError(err, file, fileList) {
        this.uploadProgressFlag = false;
        this.$message({
          message: '上传文件失败',
          type: 'warning'
        });
      }
    }
  }
</script>

<style scoped>

</style>
