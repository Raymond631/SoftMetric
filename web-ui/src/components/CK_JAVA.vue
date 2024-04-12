<template>
  <div>
    <h1>CK-JAVA</h1>
    <el-upload
        drag
        action="/api/upload"
        accept=".java"
        :on-success="handleSuccess"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传.java文件</div>
    </el-upload>

    <el-table
        v-if="data_isshow"
        :data="data"
        style="width: 100%">
      <el-table-column
          prop="class"
          label="类名"
          width="180">
      </el-table-column>
      <el-table-column
          prop="file"
          label="文件路径"
          width="180">
      </el-table-column>
      <el-table-column
          prop="type"
          label="类型"
          width="70">
      </el-table-column>
      <el-table-column
          prop="loc"
          label="代码行"
          width="70">
      </el-table-column>
      <el-table-column
          prop="noc"
          label="子类数"
          width="70">
      </el-table-column>
      <el-table-column
          prop="lcom"
          label="内聚缺乏度"
          width="70">
      </el-table-column>
      <el-table-column
          prop="wmc"
          label="类加权方法数"
          width="70">
      </el-table-column>
      <el-table-column
          prop="rfc"
          label="类响应数"
          width="70">
      </el-table-column>
      <el-table-column
          prop="dit"
          label="继承树深度"
          width="70">
      </el-table-column>
      <el-table-column
          prop="cbo"
          label="对象耦合"
          width="70">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "CK_JAVA",
  data () {
    return {
      data_isshow: false,
      data: [],
    }
  },
  methods: {
    handleSuccess (response) {
      console.log(response)
      if(response.success){
        this.success(response.msg)
        this.analyse(response.data)
      }else{
        this.warn(response.msg)
      }
    },
    analyse(path){
      axios.get('/api/CKJava', {
        params: {
          path:path
        }
      }).then(response=>{
        console.log(response)
        if(response.data.success){
          this.success(response.data.msg)
          this.data = response.data.data
          console.log(this.data)
          this.data_isshow = true
        }else{
          this.warn("文件格式无效，请修改后重试")
        }
      })
    },
    success(message) {
      this.$message({
        message: message,
        type: 'success'
      });
    },
    warn(message) {
      this.$message({
        message: message,
        type: 'warning'
      });
    },
  },
}
</script>

<style scoped>

</style>