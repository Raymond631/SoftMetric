<template>
  <!--  顶部导航栏-->
  <navigationBar />
  <div class="container">
    <div class="header">
      <div class="header_name"></div>
      <el-upload
        class="upload"
        :http-request="upload"
        :limit="1"
        accept="*.xlsx"
        action="string"
        multiple
      >
        <button size="small" class="upload-btn">
          <div class="upload-content">upload</div>
        </button>
      </el-upload>
    </div>
    <div class="content">
      <el-scrollbar height="80vh">
        <div class="table-name">UFC计算</div>
        <el-table :data="UFCData" class="table">
          <el-table-column prop="number" label="对应个数" />
          <el-table-column prop="simple" label="Simple" />
          <el-table-column prop="average" label="Average" />
          <el-table-column prop="complex" label="Complex" />
          <el-table-column fixed="right" label="Operations">
            <template #default>
              <el-button link type="primary" size="small">Edit</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="calculator">UFC计算结果：{{ ufcResult }}</div>

        <div class="table-name">功能点度量结果：{{ result }}</div>
      </el-scrollbar>
    </div>
  </div>
</template>

<script lang="ts">
import { defineComponent } from "vue";
import navigationBar from "@/components/navigationBar.vue";
import welcomeHeader from "@/components/header.vue";
import { uploadFile } from "@/utils/common";

export default defineComponent({
  name: "controlFlow",
  data() {
    return {
      UFCData: [
        {
          number: 10,
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          number: 10,
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          number: 10,
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          number: 10,
          simple: 0,
          average: 2,
          complex: 0,
        },
        {
          number: 10,
          simple: 0,
          average: 2,
          complex: 0,
        },
      ],
      ufcResult: 100,
      vafResult: 100,
      result: 100,
    };
  },
  components: { navigationBar, welcomeHeader },
  mounted() {},
  methods: {
    ready() {},
    upload() {
      let item;
      let data = uploadFile(item);
    },
  },
});
</script>

<style lang="scss" scoped>
@import "../assets/style/css/controlFlow";
</style>
