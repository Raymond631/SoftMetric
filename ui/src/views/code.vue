<template>
  <!--  顶部导航栏-->
  <navigationBar />
  <div class="container">
    <div class="header">
      <div class="header_name">
        <!-- {{ page_name }} -->
      </div>
      <div class="upload">
        <!-- <form
        action="http://localhost:12345/uploadFiles"
        enctype="multipart/form-data"
        method="post"
      > -->
        <button size="small" class="upload-btn">
          <input
            style=""
            class="upload-content"
            ref="fileInput"
            multiple
            type="file"
            webkitdirectory
            @change="handleFileChange"
          />
        </button>
        <!-- </form> -->
      </div>
    </div>
    <div class="content">
      <el-scrollbar height="80vh">
        <div id="ck-graph" class="ck-graph" style=""></div>
        <div class="table-name">CK度量</div>
        <el-table :data="CKData" class="table">
          <el-table-column prop="className" label="ClassName" />
          <el-table-column prop="type" label="Type" />
          <el-table-column prop="wmc" label="WMC" />
          <el-table-column prop="rfc" label="RFC" />
          <el-table-column prop="dit" label="DIT" />
          <el-table-column prop="noc" label="NOC" />
          <el-table-column prop="cbo" label="CBO" />
          <el-table-column prop="lcom" label="LCOM" />
        </el-table>

        <div class="table-name">LK度量</div>
        <el-table :data="LKData" class="table">
          <el-table-column prop="className" label="ClassName" />
          <el-table-column prop="type" label="Type" />
          <el-table-column prop="cs" label="CS" />
          <el-table-column prop="noo" label="NOO" />
          <el-table-column prop="noa" label="NOA" />
          <el-table-column prop="si" label="SI" />
        </el-table>

        <div class="table-name">传统度量</div>
        <el-table :data="TRAData" class="table">
          <el-table-column prop="className" label="ClassName" />
          <el-table-column prop="type" label="Type" />
          <el-table-column prop="loc" label="LOC" />
          <el-table-column prop="cp" label="CP" />
          <el-table-column prop="cc" label="CC" />
        </el-table>
      </el-scrollbar>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import navigationBar from "@/components/navigationBar.vue";
import welcomeHeader from "@/components/header.vue";
import { metric } from "@/api/code";
import axios from "axios";
import * as echarts from 'echarts';


export default defineComponent({
  name: "code",
  data() {
    return {
      CKData: [],
      LKData: [],
      TRAData: [],
      uuid: "",
    };
  },
  components: { navigationBar, welcomeHeader },
  mounted() {},
  methods: {
    ready() {},
    handleFileChange(event) {
      this.files = event.target.files;
      this.uploadFiles();
    },
    uploadFiles() {
      let that = this;
      if (!this.files) {
        console.error("No files selected");
        return;
      }

      let formData = new FormData();
      for (let i = 0; i < this.files.length; i++) {
        // 只上传java文件
        if (this.files[i].name.endsWith(".java")) {
          formData.append("files", this.files[i]);
        }
      }
      if (formData.entries().next().done) {
        // 选择的文件夹中没有.java文件
        window.alert("请选择Java项目！");
      } else {
        let config = {
          method: "post",
          url: "/api/uploadFiles",
          data: formData,
        };
        axios(config)
          .then(function (response) {
            that.uuid = response.data.data;
            that.getData();
          })
          .catch(function (error) {
            console.log(error);
          });
      }
    },
    getData() {
      let that = this;
      metric(this.uuid)
        .then((res: any) => {
          console.log(res);
          that.CKData = res.data.ck;
          that.LKData = res.data.lk;
          that.TRAData = res.data.tradition;
        })
        .catch((err: any) => {
          console.log(err);
        });
    },
    ckGraph() {
      var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;

option = {
  title: {
    text: 'Basic Radar Chart'
  },
  legend: {
    data: ['Allocated Budget', 'Actual Spending']
  },
  radar: {
    // shape: 'circle',
    indicator: [
      { name: 'Sales', max: 6500 },
      { name: 'Administration', max: 16000 },
      { name: 'Information Technology', max: 30000 },
      { name: 'Customer Support', max: 38000 },
      { name: 'Development', max: 52000 },
      { name: 'Marketing', max: 25000 }
    ]
  },
  series: [
    {
      name: 'Budget vs spending',
      type: 'radar',
      data: [
        {
          value: [4200, 3000, 20000, 35000, 50000, 18000],
          name: 'Allocated Budget'
        },
        {
          value: [5000, 14000, 28000, 26000, 42000, 21000],
          name: 'Actual Spending'
        }
      ]
    }
  ]
};

option && myChart.setOption(option);
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../assets/style/css/code";
</style>
