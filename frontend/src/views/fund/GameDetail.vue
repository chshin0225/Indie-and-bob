<template>
  <div>
    <!-- header -->
    <div class="header">
      <v-container>
        <h1>{{ project.name }}</h1>
        <v-row>
          <v-col>
            <p>기간: {{ project.startedAt }} ~ {{ project.deadline }}</p>
            <p>목표: {{ project.aim }}원</p>
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- content -->
    <v-container>
      <p>{{ project }}</p>
      <v-row>
        <!-- tab section -->
        <v-col cols=8>
          <v-tabs fixed-tabs>
            <v-tab>소개</v-tab>
            <v-tab>Q&A</v-tab>
            <v-tab>Community</v-tab>

            <!-- 프로젝트 소개 -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>프로젝트 소개</h2>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Q&A -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>Q&A</h2>
                </v-card-text>
              </v-card>
            </v-tab-item>

            <!-- Community -->
            <v-tab-item>
              <v-card flat>
                <v-card-text>
                  <h2>Community</h2>
                </v-card-text>
              </v-card>
            </v-tab-item>
          </v-tabs>
        </v-col>

        <!-- rewards section -->
        <v-col cols=4>
          <v-card tile>
            <v-list flat>
              <v-subheader>Rewards</v-subheader>
              <v-divider></v-divider>

              <v-list-item-group color="primary">
                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>option 1</v-list-item-title>
                    option1option1option1option1option1
                  </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>

                <v-list-item>
                  <v-list-item-content>
                    <v-list-item-title>option 2</v-list-item-title>
                    option2option2option2option2option2
                  </v-list-item-content>
                </v-list-item>
                <v-divider></v-divider>
              </v-list-item-group>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  computed: {
    ...mapState(['project',])
  },

  methods: {
    ...mapActions(['getProject',])
  },

  created() {
    this.getProject(this.$route.params.id)
  },
};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>