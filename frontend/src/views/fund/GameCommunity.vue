<template>
  <v-row justify=center>
    <v-col cols="12">
      <v-card>
        <v-list two-line>
          <template v-for="article in community">
            <v-subheader :key="article.id">{{ article.content }}</v-subheader>
            <v-list-item :key="article.title" @click="">
              <v-list-item-avatar>
                <img :src="article.user.profileurl">
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title>{{article.user.username}}</v-list-item-title>
                <v-list-item-subtitle>{{article.createdat}}</v-list-item-subtitle>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-list>
      </v-card>
    </v-col>
  </v-row>
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
    data() {
      return {

      }
    }

}
</script>

<style>

</style>