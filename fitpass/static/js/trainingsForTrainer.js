Vue.component("trainer-trainings", {
    data: function () {

        return {
            facilityList: "",
            sortDirection: null,
            configHeaders: {
                headers: {
                    token: $cookies.get("token")
                }
            },
            userInfo: {
                username: "",
                role: ""
            },
            orders: "",
            canCancel: true

        }
    },
    template: ` 
<div>
    <div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
        <button v-on:click="editProfile">Profile</button>
    </div>
    
    <h1>Trainings</h1>   
    <div v-for="order in orders">
        <p>Name: {{order.name}}</p>
        <p>Type: {{order.type}}</p>
        <p>Facility: {{order.facilityName}}</p>
        <p>Time: {{order.time}}</p>
        <button v-if="order.type == 'PERSONAL'" v-on:click="cancel(order.id)">Cancel</button>
        <p v-if="!canCancel">You can't cancel in less than 2 days!</p>
    </div>
	
</div>		  
`
    ,
    methods:
        {
            editProfile() {
                router.push("/edit-profile")
            },
            cancel(id) {
                axios
                    .get('/trainer/get-trainings/cancel?id='+id, this.configHeaders)
                    .then(response => {
                        this.canCancel = response.data;
                        if(this.canCancel) {
                            location.reload();
                        }
                    })
            }
        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('/users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                })
        }
        axios
            .get('/trainer/get-trainings',this.configHeaders)
            .then(response => this.orders = response.data.orders)
    },
});