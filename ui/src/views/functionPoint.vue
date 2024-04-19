<template>
  <!--  顶部导航栏-->
  <navigationBar />
  <div class="container">
    <!-- <welcomeHeader /> -->
    <div class="content">
      <el-scrollbar height="85vh">
        <div class="table-name">step1: UFC计算</div>
        <el-table :data="UFCData" class="table">
          <el-table-column prop="name" label="请输入对应个数" />
          <el-table-column label="Simple" prop="simple">
            <template #default="scope">
              <el-input
                v-if="activeIndex == scope.$index"
                v-model="scope.row.simple"
              ></el-input>
              <span v-else>{{ scope.row.simple }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Average" prop="average">
            <template #default="scope">
              <el-input
                v-if="activeIndex == scope.$index"
                v-model="scope.row.average"
              ></el-input>
              <span v-else>{{ scope.row.average }}</span>
            </template>
          </el-table-column>
          <el-table-column label="Complex" prop="complex">
            <template #default="scope">
              <el-input
                v-if="activeIndex == scope.$index"
                v-model="scope.row.complex"
              ></el-input>
              <span v-else>{{ scope.row.complex }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column prop="name" label="对应个数" /> -->
          <!-- <el-table-column prop="simple" label="Simple" /> -->
          <!-- <el-table-column prop="average" label="Average" /> -->
          <!-- <el-table-column prop="complex" label="Complex" /> -->
          <el-table-column fixed="right" label="Operations">
            <template #default="scope">
              <div v-if="activeIndex == scope.$index">
                <el-button type="info" @click="handleSave">Save</el-button>
              </div>
              <div v-else>
                <el-button type="success" @click="handleEdit(scope.$index)"
                  >Edit</el-button
                >
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="calculator">UFC计算结果：{{ ufcResult }}</div>

        <div class="table-name">step2: VAF计算</div>
        <el-table :data="VAFData" class="table">
          <el-table-column prop="index" label="序号" />
          <el-table-column prop="character" label="系统特性" />
          <el-table-column prop="description" label="简单描述" />
          <el-table-column prop="level" label="等级">
            <template #default="scope">
              <el-input
                v-if="activeIndex == scope.$index"
                v-model="scope.row.level"
              ></el-input>
              <span v-else>{{ scope.row.level }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="Operations">
            <template #default="scope">
              <div v-if="activeIndex == scope.$index">
                <el-button type="info" @click="handleSave">Save</el-button>
              </div>
              <div v-else>
                <el-button type="success" @click="handleEdit(scope.$index)"
                  >Edit</el-button
                >
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div class="calculator">VAF计算结果：{{ vafResult }}</div>

        <div class="table-name">功能点度量结果：{{ Math.ceil(vafResult * ufcResult * 100) / 100.0 }}</div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import navigationBar from "@/components/navigationBar.vue";
import welcomeHeader from "@/components/header.vue";
import { uploadFile } from "@/utils/common";
import { Promise } from "es6-promise";

export default defineComponent({
  name: "functionPoint",
  data() {
    return {
      UFCData: [
        {
          name: "External Inputs",
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          name: "External Outputs",
          simple: 0,
          average: 2,
          complex: 1,
        },
        {
          name: "External Queries",
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          name: "Internal Logic Files",
          simple: 0,
          average: 0,
          complex: 1,
        },
        {
          name: "External Interfaces Files",
          simple: 0,
          average: 2,
          complex: 0,
        },
      ],
      VAFData: [
        {
          index: 0,
          character: "数据通讯",
          description: "软件系统的信息交互情况",
          level: 3,
        },
        {
          index: 1,
          character: "数据分布处理",
          description: "软件系统如何处理分布数据",
          level: 3,
        },
        {
          index: 2,
          character: "系统性能",
          description: "用户对软件系统的响应时间和吞吐量的要求",
          level: 0,
        },
        {
          index: 3,
          character: "使用配置",
          description: "当前硬件平台的使用度",
          level: 5,
        },
        {
          index: 4,
          character: "事务处理频率",
          description: "每日、每周以及每月等的事务处理情况",
          level: 0,
        },
        {
          index: 5,
          character: "在线数据输入",
          description: "在线信息输入的百分比",
          level: 3,
        },
        {
          index: 6,
          character: "操作便利",
          description: "软件系统是否为用户操作提供便利",
          level: 3,
        },
        {
          index: 7,
          character: "在线更新",
          description: "多少ILF通过在线事务更新",
          level: 3,
        },
        {
          index: 8,
          character: "处理复杂度",
          description: "软件系统是否广泛使用逻辑和数学运算",
          level: 0,
        },
        {
          index: 9,
          character: "复用性",
          description: "本软件系统是否为多个应用系统复用",
          level: 5,
        },
        {
          index: 10,
          character: "安装难易",
          description: "版本维护和安装的难易程度",
          level: 0,
        },
        {
          index: 11,
          character: "运行维护",
          description: "软件系统的启动、备份以及恢复的难易程度",
          level: 0,
        },
        {
          index: 12,
          character: "多站点支持",
          description: "软件系统是否用于多组织和多站点",
          level: 0,
        },
        {
          index: 13,
          character: "变更支持",
          description: "软件系统对查询、统计以及变更维护的支持",
          level: 3,
        },
      ],
      activeIndex: -1,
      files: [],
    };
  },
  computed: {
    ufcResult: function () {
      let fp_ufc_data = this.UFCData;
      let res =
        fp_ufc_data[0].simple * 3 +
        fp_ufc_data[0].average * 4 +
        fp_ufc_data[0].complex * 6 +
        fp_ufc_data[1].simple * 4 +
        fp_ufc_data[1].average * 5 +
        fp_ufc_data[1].complex * 7 +
        fp_ufc_data[2].simple * 3 +
        fp_ufc_data[2].average * 4 +
        fp_ufc_data[2].complex * 6 +
        fp_ufc_data[3].simple * 5 +
        fp_ufc_data[3].average * 7 +
        fp_ufc_data[3].complex * 10 +
        fp_ufc_data[4].simple * 7 +
        fp_ufc_data[4].average * 10 +
        fp_ufc_data[4].complex * 15;
      return res;
    },

    vafResult: function () {
      let res = 0;
      let fp_vaf_data = this.VAFData;
      for (let i = 0; i < 14; i++) {
        res += fp_vaf_data[i].level * 1;
      }
      res = res * 0.01 + 0.65;
      return res;
    },
  },
  components: { navigationBar, welcomeHeader },
  mounted() {},
  methods: {
    ready() {},
    upload(item: any) {
      console.log(item);

      let data = uploadFile(item);
    },
    handleEdit(index: number) {
      this.activeIndex = index;
      console.log(this.activeIndex);
    },
    handleSave() {
      this.activeIndex = -1;
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../assets/style/css/functionPoint";
</style>
