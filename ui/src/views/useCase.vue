<template>
  <!--  顶部导航栏-->
  <navigationBar />
  <div class="container">
    <!-- <welcomeHeader /> -->
    <div class="content">
      <el-scrollbar height="85vh">
        <div class="table-name">Step1: UAW计算</div>
        <div class="lineCal">
          <span>简单角色个数</span>
          <input v-model="simple_value_uaw" />
          <span>普通角色个数</span>
          <input v-model="average_value_uaw" />
          <span>复杂角色个数</span>
          <input v-model="complex_value_uaw" />
        </div>
        <div class="calculator">UAW计算结果：{{ uawResult }}</div>
        <div class="table-name">Step2: UUC计算</div>
        <div class="lineCal">
          <span>简单用例个数</span>
          <input v-model="simple_value_uuc" />
          <span>普通用例个数</span>
          <input v-model="average_value_uuc" />
          <span>复杂用例个数</span>
          <input v-model="complex_value_uuc" />
        </div>
        <div class="calculator">UUC计算结果：{{ uucResult }}</div>

        <div class="table-name">Step3: TCF计算</div>
        <el-table :data="TCFData" class="table">
          <el-table-column prop="id" label="技术复杂性Ti" />
          <el-table-column prop="feature" label="说明" />
          <el-table-column prop="weight" label="权重WF" />
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
        <div class="calculator">
          TCF计算结果：{{ Math.ceil(tcfResult * 1000) / 1000.0 }}
        </div>

        <div class="table-name">Step4: EF计算</div>
        <el-table :data="EFData" class="table">
          <el-table-column prop="id" label="技术复杂性Ti" />
          <el-table-column prop="description" label="说明" />
          <el-table-column prop="weight" label="权重WF" />
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
        <div class="calculator">
          EF计算结果：{{ Math.ceil(efResult * 1000) / 1000.0 }}
        </div>

        <div class="table-name">
          UPC结果：{{
            Math.ceil((uucResult + uawResult) * tcfResult * efResult * 1000) /
            1000.0
          }}
        </div>
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
  name: "functionPoint",
  data() {
    return {
      simple_value_uuc: 4,
      average_value_uuc: 5,
      complex_value_uuc: 1,
      simple_value_uaw: 1,
      average_value_uaw: 1,
      complex_value_uaw: 2,

      TCFData: [
        {
          key: 0,
          id: "T1",
          feature: "分布式系统",
          weight: 2,
          level: 3,
        },
        {
          key: 1,
          id: "T2",
          feature: "响应或吞吐量性能",
          weight: 1,
          level: 5,
        },
        {
          key: 2,
          id: "T3",
          feature: "终端用户效率",
          weight: 1,
          level: 3,
        },
        {
          key: 3,
          id: "T4",
          feature: "复杂的内部结构",
          weight: 1,
          level: 3,
        },
        {
          key: 4,
          id: "T5",
          feature: "代码必须重用",
          weight: 1,
          level: 3,
        },
        {
          key: 5,
          id: "T6",
          feature: "易安装性",
          weight: 0.5,
          level: 3,
        },
        {
          key: 6,
          id: "T7",
          feature: "易用性",
          weight: 0.5,
          level: 3,
        },
        {
          key: 7,
          id: "T8",
          feature: "可移植性",
          weight: 2,
          level: 3,
        },
        {
          key: 8,
          id: "T9",
          feature: "易更改性",
          weight: 1,
          level: 3,
        },
        {
          key: 9,
          id: "T10",
          feature: "并发性",
          weight: 1,
          level: 3,
        },
        {
          key: 10,
          id: "T11",
          feature: "特殊的安全性",
          weight: 1,
          level: 5,
        },
        {
          key: 11,
          id: "T12",
          feature: "提供第三方接口",
          weight: 1,
          level: 3,
        },
        {
          key: 12,
          id: "T13",
          feature: "需要特别的用户培训",
          weight: 1,
          level: 3,
        },
      ],
      EFData: [
        {
          key: 0,
          id: "F1",
          description: "熟悉UML的程度",
          weight: 1.5,
          level: 3,
        },
        {
          key: 1,
          id: "F2",
          description: "开发应用程序的经验",
          weight: 0.5,
          level: 3,
        },
        {
          key: 2,
          id: "F3",
          description: "面向对象的经验",
          weight: 1,
          level: 3,
        },
        {
          key: 3,
          id: "F4",
          description: "主分析师的能力",
          weight: 0.5,
          level: 3,
        },
        {
          key: 4,
          id: "F5",
          description: "激励机制",
          weight: 1,
          level: 3,
        },
        {
          key: 5,
          id: "F6",
          description: "需求稳定度",
          weight: 2,
          level: 3,
        },
        {
          key: 6,
          id: "F7",
          description: "具有兼职人员",
          weight: -1,
          level: 0,
        },
        {
          key: 7,
          id: "F8",
          description: "具有复杂编程",
          weight: -1,
          level: 3,
        },
      ],
      activeIndex: -1,
    };
  },
  computed: {
    uucResult: function () {
      return (
        Number(this.simple_value_uuc) * 5 +
        Number(this.average_value_uuc) * 10 +
        Number(this.complex_value_uuc) * 15
      );
    },
    uawResult: function () {
      return (
        Number(this.simple_value_uaw) +
        Number(this.average_value_uaw) * 2 +
        Number(this.complex_value_uaw) * 3
      );
    },
    tcfResult: function () {
      let res = 0;
      let fp_tcf_data = this.TCFData;
      for (let i = 0; i < 13; i++) {
        res += fp_tcf_data[i].level * fp_tcf_data[i].weight;
      }
      res = res * 0.01 + 0.6;
      return res;
    },
    efResult: function () {
      let res = 0;
      let fp_ef_data = this.EFData;
      for (let i = 0; i < 8; i++) {
        res += fp_ef_data[i].weight * 1 * (fp_ef_data[i].level * 1) * 1;
      }
      res = res * -0.03 + 1.4;
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
@import "../assets/style/css/useCase";
</style>
