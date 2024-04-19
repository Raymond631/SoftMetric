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
        <div id="ck-graph" class="ck-graph" v-show="graph"></div>
        <div class="table-name">CK度量</div>
        <el-table :data="CKData" class="table" @row-click="openDetails">
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
import * as echarts from "echarts";

export default defineComponent({
  name: "code",
  data() {
    return {
      CKData: [],
      LKData: [],
      TRAData: [],
      uuid: "",
      graph:false
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
          that.ckGraph();
        })
        .catch((err: any) => {
          console.log(err);
        });
    },
    ckGraph(ckGraphData, ckName) {
      let chartDom = document.getElementById("ck-graph");
      let myChart = echarts.init(chartDom);
      let option;
      this.graph = true;
      // for (const i of this.CKData) {
      //   let temp = { value: [], name: "" };
      //   temp.name = i.className;
      //   temp.value.push(i.wmc);
      //   temp.value.push(i.rfc);
      //   temp.value.push(i.dit);
      //   temp.value.push(i.noc);
      //   temp.value.push(i.cbo);
      //   temp.value.push(i.lcom);
      //   ckGraphData.push(temp);
      //   ckName.push(i.className);
      // }
      // console.log(ckGraphData);
      option = {
        title: {
          text: "ck Chart",
        },
        legend: {
          data: ckName,
        },
        radar: {
          // shape: 'circle',
          indicator: [
            { name: "WMC", max: 10 },
            { name: "RFC", max: 30 },
            { name: "DIT", max: 5 },
            { name: "NOC", max: 10 },
            { name: "CBO", max: 20 },
            { name: "LCOM", max: 10 },
          ],
        },
        series: [
          {
            name: "ck",
            type: "radar",
            data: ckGraphData,
          },
        ],
      };
      myChart.setOption({
        grid: {
          width: "30%",
          height: "50%",
        },
      });
      option && myChart.setOption(option);
    },
    openDetails(row) {
      //具体操作
      console.log(row);
      let ckGraphData = [];
      let ckName = [];
      let temp = { value: [], name: "" };
      temp.name = row.className;
      temp.value.push(row.wmc);
      temp.value.push(row.rfc);
      temp.value.push(row.dit);
      temp.value.push(row.noc);
      temp.value.push(row.cbo);
      temp.value.push(row.lcom);
      ckGraphData.push(temp);
      ckName.push(row.className);
      this.ckGraph(ckGraphData,ckName)
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../assets/style/css/code";
</style>
