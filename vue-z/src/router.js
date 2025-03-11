import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Register from '@/components/registerpage/Register'
import Movie from '@/components/blogpage/Movie'
import Recommand from "@/components/recomandpage/Recommand"
import MoviePlay from "@/components/common/MoviePlay"
import Login from "@/components/LoginPage/Login";
import MovieSet from "@/components/moviepage/MovieSet";
import SearchResult from "@/components/searchResultPage/SearchResult";
import AdminIndex from "@/components/admin/AdminIndex";
import MovieManagement from "@/components/admin/content/MovieManagement";
import UserProfile from "@/components/admin/user/UserProfile";
import Role from "@/components/admin/user/Role";
import ErrorPage from "@/components/common/ErrorPage";
import Profile from "@/components/profilepage/Profile";
import Profile1 from "@/components/profilepage/Profile1";
import Friends from "@/components/friends/Friends";
import Preference from "@/components/preference/Preference";
import History from "@/components/historypage/History";
import Privacy from "@/components/privacy/Privacy";
import History1 from "@/components/historypage/History1";

Vue.use(Router)

export default new Router({
    mode: 'history',
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home,
            meta: {
                keepAlive: true,
                ishome:true
            }
        },
        {
            path: '/admin',
            name: 'AdminIndex',
            component: AdminIndex,
            meta: {
                keepAlive: false,
                requireAuth: true
            },
            children : [
                {
                  path: 'content/movie',
                  name: 'MovieManagement',
                  component: MovieManagement,
                  meta: {
                      requireAuth: true
                  }
                },
                {
                    path: 'user/profile',
                    name: 'UserProfile',
                    component: UserProfile,
                    meta: {
                        requireAuth: true
                    }
                },
                {
                    path: 'user/role',
                    name: 'Role',
                    component: Role,
                    meta: {
                        requireAuth: true
                    }
                }
            ]
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: {
                keepAlive: false
            }
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
            meta: {
                keepAlive: false
            }
        },
        {
            path: '/preference',
            name: 'Preference',
            component: Preference,
            meta: {
                keepAlive: false
            }
        },
        {
            path: '/movie',
            name: 'Movie',
            component: Movie,
            meta: {
                keepAlive: true,
                requireAuth: true,
                ishome:false
            }
        },
        {
            path: '/movieset',
            name: 'MovieSet',
            component: MovieSet,
            meta: {
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/recommand',
            name: 'Recommand',
            component: Recommand,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/profile',
            name: 'Profile',
            component: Profile,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/profile1',
            name: 'Profile1',
            component: Profile1,
            meta: {
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/friends',
            name: 'Friends',
            component: Friends,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/history',
            name: 'History',
            component: History,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/history1',
            name: 'History1',
            component: History1,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/privacy',
            name: 'Privacy',
            component: Privacy,
            meta: {
                requireAuth: true,
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/movieplay',
            name: 'MoviePlay',
            component: MoviePlay,
            meta: {
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/search',
            name: 'SearchResult',
            component: SearchResult,
            meta: {
                keepAlive: true,
                ishome:false
            }
        },
        {
            path: '/404',
            name: 'ErrorPage',
            component: ErrorPage,
            meta: {
                keepAlive: false
            }
        },
        {
            path: '*',
            redirect: '/404',
        }
    ]
})
