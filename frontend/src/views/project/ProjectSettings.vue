<template>
  <v-card>
    <v-toolbar flat color="primary" dark>
      <v-toolbar-title>내 프로젝트</v-toolbar-title>
    </v-toolbar>
    <v-tabs vertical>
      <v-tab>내 Community</v-tab>
      <v-tab>내 Q&A</v-tab>
      <v-tab>리워드 구매내역</v-tab>

      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <p>Sed aliquam ultrices mauris. Donec posuere vulputate arcu. Morbi ac felis. Etiam feugiat lorem non metus. Sed a libero.</p>

            <p>Nam ipsum risus, rutrum vitae, vestibulum eu, molestie vel, lacus. Aenean tellus metus, bibendum sed, posuere ac, mattis non, nunc. Aliquam lobortis. Aliquam lobortis. Suspendisse non nisl sit amet velit hendrerit rutrum.</p>

            <p
              class="mb-0"
            >Phasellus dolor. Fusce neque. Fusce fermentum odio nec arcu. Pellentesque libero tortor, tincidunt et, tincidunt eget, semper nec, quam. Phasellus blandit leo ut odio.</p>
          </v-card-text>
        </v-card>
      </v-tab-item>
      <v-tab-item>
        <SettingsQnA />
      </v-tab-item>
      <v-tab-item>
        <v-card flat>
          <v-card-text>
            <p>Fusce a quam. Phasellus nec sem in justo pellentesque facilisis. Nam eget dui. Proin viverra, ligula sit amet ultrices semper, ligula arcu tristique sapien, a accumsan nisi mauris ac eros. In dui magna, posuere eget, vestibulum et, tempor auctor, justo.</p>

            <p
              class="mb-0"
            >Cras sagittis. Phasellus nec sem in justo pellentesque facilisis. Proin sapien ipsum, porta a, auctor quis, euismod ut, mi. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nam at tortor in tellus interdum sagittis.</p>
          </v-card-text>
        </v-card>
      </v-tab-item>
    </v-tabs>
  </v-card>
</template>

<script>
// import { mapActions, mapMutations, mapState, mapGetters } from 'vuex'
// import axios from 'axios';
// import SERVER from '../../api/base'
import router from "../../router";
import SettingsQnA from "../../components/ProjectSettings/SettingsQnA.vue";

export default {
  components: {
    SettingsQnA,
  },
  created() {
    if (this.project.user != localStorage.getItem("username")) {
      router.push("/405");
    }
  },
};
</script>

<style>
</style>