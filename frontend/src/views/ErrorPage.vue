<template>
  <v-container>
      <v-row class="justify-center">
          <v-col cols=12>
              <h1 class="text-center"> {{ errorDetail }} Error ! ! 
                  <br> use another {{ errorDetail }}</h1>
          </v-col>
          <v-col class="justify-center" cols=auto>
              <v-btn @click="goBack" color="primary">back</v-btn>
          </v-col>
      </v-row>
  </v-container>
</template>

<script>
import router from "../router"
import { mapState } from 'vuex'

export default {
    methods: {
        goBack () {
            router.go('-1')
        }
    },
    computed: {
        ...mapState([ 'errorDetail' ])
    }
}
</script>

<style>

</style>