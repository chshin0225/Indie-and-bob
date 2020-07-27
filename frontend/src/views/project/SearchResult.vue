<template>
  <div>
    <v-tabs v-model="tab" background-color="primary" class="elevation-2" dark centered grow>
      <v-tabs-slider></v-tabs-slider>
      <v-tab>통합검색</v-tab>
      <v-tab>게임/프로젝트</v-tab>
      <v-tab>유저</v-tab>
      <v-tab>커뮤니티</v-tab>
      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>통합검색 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>
      
      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>게임 / 프로젝트 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>
      
      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>커뮤니티 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>
      
      <v-tab-item>
        <v-card flat outlined>
          <v-card-text>유저검색 부분입니다.</v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </div>
</template>

<script>
export default {
  data() {
    return {
      tab: null,
      tabs: 4,
    };
  },
};
</script>

<style>
</style>