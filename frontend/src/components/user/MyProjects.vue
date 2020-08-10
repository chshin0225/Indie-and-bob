<template>
  <v-container>
    <v-row>
      <v-col v-for="project in myProjectList" :key="project.gameId" cols=6 sm=4>
        <v-card>
          <router-link :to="`/game/${project.gameId}`" class="text-decoration-none">
              <v-list-item>
                <v-avatar>
                  <img src="../../assets/default_profile.png" :alt="project.nickname" />
                </v-avatar>
                <v-list-item-content class="ml-4">
                  <v-list-item-title class="headline">{{ project.name }}</v-list-item-title>
                  <router-link class="text-decoration-none" :to="`/user/mypage/${project.nickname}`">{{ project.nickname }}</router-link>
                  <v-list-item-subtitle>{{ $moment(project.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
                  <v-list-item-subtitle>목표액: {{ project.aim }}</v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
              <v-img src="../../assets/default_project.png" height="194"></v-img>
          </router-link>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-heart</v-icon>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <!-- <infinite-loading @infinite="fetchMyProjects"></infinite-loading> -->
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'
// import SERVER from '../../api/base'
// import axios from 'axios'

// import InfiniteLoading from "vue-infinite-loading"
// import firebase from 'firebase'

export default {
  name: 'MyProjects',

  // components: {
  //   InfiniteLoading,
  // },

  data() {
    return {
      projectNum: 0,
      // myProjectList: [],
    }
  },

  computed: {
    ...mapState(['myProjectList'])
  },

  methods: {
    ...mapActions(['fetchMyProjects',]),

    // fetchMyProjects($state) {
    //   const storageRef = firebase.storage().ref()
    //   axios.get(SERVER.BASE + SERVER.MYPROJECT + `${this.$route.params.username}/${this.projectNum}`)
    //     .then(res => {
    //       console.log('fetch', res.data)
    //       if (res.data.object.length > 0) {
    //         this.projectNum += 9;
    //         console.log(res.data);
    //         res.data.object.forEach(item => {
    //           console.log(item)
    //           if (item.profile !== null) {
    //             storageRef.child(item.profile).getDownloadURL().then(url => {
    //               item.profile = url
    //             })
    //           }
    //           if (item.thumbnail !== null) {
    //             storageRef.child(item.thumbnail).getDownloadURL().then(url => {
    //               item.thumbnail = url
    //             })
    //           } 
    //           this.myProjectList.push(item);
    //         });
    //         $state.loaded();
    //       } else {
    //         $state.complete();
    //       }
    //     })
    //     .catch(err => console.error(err))
    // },
  },

  created() {
    this.fetchMyProjects(this.$route.params.username)
  },
}
</script>

<style>

</style>