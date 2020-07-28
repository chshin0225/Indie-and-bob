<template>
  <v-container>

    <!-- <p>{{ projectList }}</p> -->
    <h1 class="text-center">Projects</h1>

    <!-- projects  -->
    <div v-for="game in games" :key="game.gameId">
      <v-card outlined :to="`/game/${game.gameId}`">
        <v-list-item three-line>
          <v-list-item-content>
            <v-list-item-title class="headline mb-1">{{ game.name }}</v-list-item-title>
            <v-list-item-subtitle>{{ game.deadline }}까지</v-list-item-subtitle>
            <v-list-item-subtitle>목표액: {{ game.aim }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-card>

    </div>
  <infinite-loading @infinite="infiniteHandler"></infinite-loading>
  </v-container>
</template>

<script>
import axios from 'axios'
import SERVER from '../../api/base'
import InfiniteLoading from 'vue-infinite-loading';
import { mapGetters } from 'vuex'
export default {
    data () {
        return {
            games: [],
            gameNum: 0,
        }
    },
    computed: {
        ...mapGetters([ 'headersConfig' ])
    },
    components : {
        InfiniteLoading
    },
    methods: {
        infiniteHandler($state) {
            axios.get(SERVER.BASE + SERVER.UNAPPROVED + this.gameNum + '/', this.headersConfig)
                .then(res => { 
            console.log(res)
            if (res.data.object.length > 0) {
            this.gameNum += 10;
            console.log(res.data)
            res.data.object.forEach(item => {
                this.games.push(item)
            })
            console.log('게임즈')
            console.log(this.games)
            $state.loaded();
            } else {
            $state.complete();
            }
      })
        .catch(err => console.error(err))
    },
  }
}
</script>

<style>

</style>