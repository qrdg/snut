import { createRouter, createWebHistory  } from 'vue-router';

import MainWithLogin from "@/views/MainWithLogin.vue";

import UserCollection from "@/views/UserCollection.vue";
import SearchCollections from '@/views/SearchCollections.vue';

import MyCollection from '@/views/MyCollection.vue';
import MyNote from "@/views/MyNote.vue";
import MakeNote from '@/views/MakeNote.vue'
import StoreCollections from '@/views/StoreCollections.vue';
import MakeCommunity from '@/views/MakeCommunity.vue';
import MakeCollection from '@/views/MakeCollection.vue';
import MakeColSave from '@/views/MakeColSave.vue';

import About from "@/views/About.vue";
import Faq from "@/views/Faq.vue";
import Myprofile from "@/views/Myprofile";
import Feedback from "@/views/Feedback";

import MyCommunity from "@/views/MyCommunity";
import Community from "@/views/Community";
import CommunityInside from "@/views/CommunityInside.vue";

import Login from "@/views/Login.vue";
import Forgotpw from "@/views/Forgotpw.vue";
import Changepw from "@/views/Changepw.vue";
import Join from "@/views/Join.vue";


const routes = [
  {
    path: "/",
    component: MainWithLogin,
    name: "MainWithLogin"
  },
  {
    path: "/col",
    component: SearchCollections,
    name: "SearchCollections"
  },
  {
    path: "/ucol/:id/:nickName",
    component: UserCollection,
    name: "UserCollection"
  },
  {
    path: "/mcol/main",
    component: MyCollection,
    name: "MyCollection"
  },
  { 
    path: '/mcol/note/makenote',
    component: MakeNote,
    name: "MakeNote"
  },
  { 
    path: '/mcol/mc',
    component: MakeCollection,
    name: "MakeCollection"
  },
  { 
    path: '/mcol/mc/save',
    component: MakeColSave,
    name: "MakeColSave"
  },
  { 
    path: '/mcol/note',
    component: MyNote,
    name: "MyNote"
  },
  { 
    path: '/mcol/store',
    component: StoreCollections,
    name: "StoreCollections"
  },
  { 
    path: '/mcol/mc',
    component: MakeCollection,
    name: "MakeCollection"
  },
  {
    path:"/ab",
    component: About,
    name: "About"
  },
  {
    path:"/faq",
    component: Faq,
    name: "Faq"
  },
  {
    path:"/pf",
    component: Myprofile,
    name: "Myprofile"
  },
  {
    path:"/fb",
    component: Feedback,
    name: "Feedback"
  },
  {
    path:"/mc",
    component: MyCommunity,
    name: "MyCommunity"
  },
  {
    path:"/com",
    component: Community,
    name: "Community"
  },
  {
    path:"/com/in/:communityNo",
    component: CommunityInside,
    name: "CommunityInside"
  },
  {
    path:"/com/mcom",
    component: MakeCommunity,
    name: "MakeCommunity"
  },
  {
    path: "/logi",
    component: Login,
    name:"Login"
  },
  {
    path:"/fgpw",
    component: Forgotpw,
    name: "Forgotpw"
  },
  {
    path:"/chpw",
    component: Changepw,
    name: "Changepw"
  },
  {
  path:"/regi",
    component: Join,
    name:"Join"
  }

];

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router;