<template>
  <el-menu :default-active="activeIndex" class="menu" @select="menuSelect">
    <div class="title">
      <div class="title_icon">
        <el-icon> <Folder /><Message /> </el-icon>
      </div>
      <div class="title_name">软件度量</div>
      <div class="title_groupName">木子石石石</div>
    </div>
    <h4 class="menu_title">{{ place }}</h4>
    <el-menu-item index="0" class="menu_item">
      <el-icon class="menu_item_icon">
        <Reading />
      </el-icon>
      <span class="menu_item_content">功能点度量</span>
    </el-menu-item>
    <el-menu-item index="1" class="menu_item">
      <el-icon class="menu_item_icon">
        <Reading />
      </el-icon>
      <span class="menu_item_content">用例图点度量</span>
    </el-menu-item>
    <el-menu-item index="2" class="menu_item">
      <el-icon class="menu_item_icon">
        <Reading />
      </el-icon>
      <span class="menu_item_content">代码度量</span>
    </el-menu-item>
  </el-menu>
</template>

<script lang="ts">
import { defineComponent } from "vue";

export default defineComponent({
  name: "navigationBar",
  data() {
    return {
      activeIndex: "",
      user: {
        username: "yueyue",
        roleId: 1,
        roleName: "系统管理员",
      },
      userType: 1,
      place: "index",
    };
  },
  mounted() {
    this.ready();
  },
  methods: {
    ready() {
      //刷新页面保持侧边栏选中值
      // sessionStorage.setItem("user", JSON.stringify(this.user));
      let route = this.$route.path;
      switch (route) {
        case "/":
          this.activeIndex = "0";
          this.place = "functionPoint";
          break;
        case "/useCase":
          this.activeIndex = "1";
          this.place = "useCase";
          break;
        case "/code":
          this.activeIndex = "2";
          this.place = "code";
          break;
        default:
          this.activeIndex = "0";
          this.place = "functionPoint";
          break;
      }
    },
    menuSelect(index: string) {
      //选中侧边栏后跳转到相应界面
      switch (index) {
        case "0":
          this.$router.push("/");
          this.activeIndex = "0";
          this.place = "functionPoint";
          break;
        case "1":
          this.$router.push("/useCase");
          this.activeIndex = "4";
          this.place = "useCase";
          break;
        case "2":
          this.$router.push("/code");
          this.activeIndex = "3";
          this.place = "code";
          break;
        default:
          this.$router.push("/");
          this.activeIndex = "0";
          this.place = "functionPoint";
          break;
      }
    },
  },
});
</script>

<style lang="scss" scoped="scoped">
@import "../assets/style/components/navigationBar";
</style>
