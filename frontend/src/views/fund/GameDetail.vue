<template>
  <div>
    <!-- header -->
    <div class="header">
      <v-container>
        <h1>Project title</h1>
        <v-row>
          <v-col>
            <p>기간, 목표 등등 정보</p>
          </v-col>
        </v-row>
      </v-container>
    </div>

    <!-- content -->
    <v-container>
      <v-row>
        <!-- tab section -->
        <v-col cols="8">
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
        <v-col cols="4">
          <v-card tile>
            <v-list flat>
              <v-subheader>Rewards</v-subheader>
              <v-divider></v-divider>
              <v-expansion-panels>
                <v-expansion-panel v-for="reward in rewards" :key="reward.id">
                  <v-expansion-panel-header>{{reward.name}}</v-expansion-panel-header>
                  <v-expansion-panel-content>
                    {{reward.content}}<br>가격:{{reward.price}}원<br>남은 수량 : {{reward.left}}
                  <v-btn color="primary">구매하러 가기</v-btn>
                  </v-expansion-panel-content>
                  
                </v-expansion-panel>
              </v-expansion-panels>
            </v-list>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
export default {
  
  data() {
    return{
      rewards:[{
        id: 1,
        name: '1번리워드',
        content: '1번 리워드에 대한 설명입니다.',
        left:999,
        price: 10000,
        thumbnail: 'n',
      },
      {
        id: 2,
        name: '2번리워드',
        content: '2번 리워드에 대한 설명입니다.',
        left:999,
        price: 10000,
        thumbnail: 'n',
      },{
        id: 3,
        name: '3번리워드',
        content: '3번 리워드에 대한 설명입니다.',
        left:99,
        price: 10000,
        thumbnail: 'n',
      },{
        id: 4,
        name: '4번리워드',
        content: '4번 리워드에 대한 설명입니다.',
        left:999,
        price: 10000,
        thumbnail: 'n',
      },{
        id: 5,
        name: '5번리워드',
        content: '5번 리워드에 대한 설명입니다.',
        left:999,
        price: 10000,
        thumbnail: 'n',
      },
      ]
    }
  }


};
</script>

<style scoped>
.header {
  background-color: #e4dfda;
}
</style>