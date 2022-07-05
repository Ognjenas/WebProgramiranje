const FacilityList = { template: '<facility-list></facility-list>' }
const Contact = { template: '<contact></contact>' }
const CustomerRegistration = { template: '<customer-registration></customer-registration>' }
const Login = { template: '<login></login>' }
const FacilityCreate = { template: '<facility-create></facility-create>' }
const UsersList = { template: '<users-list></users-list>' }
const CreateTrainer = { template: '<create-trainer></create-trainer>' }
const CreateManager = { template: '<create-manager></create-manager>' }
const FacilityShow= {template: '<facility-show></facility-show>'}
const EditProfile= {template: '<edit-profile></edit-profile>'}
const OpenFacility= {template: '<open-facility></open-facility>'}
const CreateOffer= {template: '<create-offer></create-offer>'}
const TrainersList= {template: '<trainers-list></trainers-list>'}

const router = new VueRouter({
    mode: 'hash',
    routes: [
        {path: '/', component: FacilityList},
        {path: '/contact', component: Contact},
        {path: '/customer-registration', component: CustomerRegistration},
        {path: '/login', component: Login},
        {path: '/facility-create', component: FacilityCreate},
        {path: '/users-list', component: UsersList},
        {path: '/create-trainer', component: CreateTrainer},
        {path: '/create-manager', component: CreateManager},
        {path: '/facility-show/:id', component: FacilityShow},
        {path: '/edit-profile', component: EditProfile},
        {path: '/open-facility', component: OpenFacility},
        {path: '/open-facility/create-offer', component: CreateOffer},
        {path: '/open-facility/trainers', component: TrainersList}
    ]
});


var app = new Vue({
    router,
    el: '#main'
});

