<template>
<v-expansion-panels>
    <v-expansion-panel
      v-for="article in community"
      :key="article.id"
    >
      <v-expansion-panel-header>{{article.title}}</v-expansion-panel-header>
      <v-expansion-panel-content>
        {{article.content}}
        {{article.user.username}}
        <v-divider></v-divider>
        <!-- 여기에 코멘트 넣을 건데 스프링 넣는 방법을 몰라서... -->
      </v-expansion-panel-content>
    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
import axios from 'axios';
import SERVER from '../../api/base';
export default {
    name:'GameCommunity',
    created() {
      axios.get(SERVER.BASE + "gamecommunityurl")
      .then(res => {
          this.community = res.data.community

      })
      .catch(err =>
      console.error(err))
    },

}
</script>

<style>

</style>