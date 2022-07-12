Vue.component("open-facility", {
    data: function () {

        return {
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }

            },
            currentFacility: {
                location: {},
                openTime: {
                    startWorkingDays: {},
                    endWorkingDays: {},
                    startSaturday: {},
                    endSaturday: {},
                    startSunday: {},
                    endSunday: {}
                }
            },
            id: "",
            userInfo: {
                username: "",
                role: ""
            },
            offers: {},
            loadedComments:"",
        }
    },
    template: ` 
<div>
<div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
        <button v-on:click="editProfile">Profile</button>
    </div>
    <div v-if="$cookies.get('token') == null">
        <button v-on:click="login">Login</button>
    </div>
    <div class="facility-list-container">
    <h1>My facility</h1>
    <table class="show-facilities-table">
    <input type="hidden" id="geoLen2" v-model="currentFacility.location.geoLength">
    <input type="hidden" id="geoWidth2" v-model="currentFacility.location.geoWidth">
        <tr>
        <td><label>Name</label></td>
        <td><label>{{currentFacility.name}}</label></td>
        </tr>
        
        <tr>
        <td><label>Type</label></td>
        <td><label>{{currentFacility.type}}</label></td>
        </tr>
        
        <tr>
        <td><label>Image</label></td>
        <td><img :src="currentFacility.imgSource" width="100" height="100"></td>
        </tr>
        
        <tr>
        <td><label>Location</label></td>
        <td> 
            <div id="map3" class="map"></div>
		    <p>City:{{currentFacility.location.city}}-Street:{{currentFacility.location.street}}-Nr:{{currentFacility.location.strNumber}}-Postal:{{currentFacility.location.postalCode}}</p></td>
        </tr>
        
        <tr>
        <td><label>isOpen</label></td>
        <td><label>{{currentFacility.isOpen}}</label></td>
        </tr>
        
        <tr>
        <td><label>Average Grade</label></td>
        <td><label>{{currentFacility.averageGrade}}</label></td>
        </tr>
        
        <tr>
        <td><label>Working Hours</label></td>
        <td>
            <p>Monday-Friday: From {{currentFacility.openTime.startWorkingDays.hour}}:{{currentFacility.openTime.startWorkingDays.minute}} to {{currentFacility.openTime.endWorkingDays.hour}}:{{currentFacility.openTime.endWorkingDays.minute}}</p>
		    <p>Saturday: From {{currentFacility.openTime.startSaturday.hour}}:{{currentFacility.openTime.startSaturday.minute}} to {{currentFacility.openTime.endSaturday.hour}}:{{currentFacility.openTime.endSaturday.minute}}</p>
		    <p>Sunday: From {{currentFacility.openTime.startSunday.hour}}:{{currentFacility.openTime.startSunday.minute}} to {{currentFacility.openTime.endSunday.hour}}:{{currentFacility.openTime.endSunday.minute}}</p></td>
        </tr>
        <tr><td>    
        <button class="login-button" v-on:click="createOffer">Create offer</button>
        <button class="login-button" v-on:click="getTrainers">Trainers</button></td>
        <td>
        <button class="login-button" v-on:click="getTrainings">Trainings</button>
        <button class="login-button" v-on:click="getCustomers">Customers</button>
        </td>
        </tr>
    </table>
    <div>
    <h2>Offers</h2>
    <table class="show-facilities-table">
	<tr>
		<th>Name</th>
		<th>Type</th>
	</tr>
		
	<tr v-for="offer in offers" >
		<td>{{offer.name}}</td>
		<td>{{offer.type}}</td>
		<td><button class="login-button" v-on:click="editOffer(offer.id)">Edit</button></td>
	</tr>
</table>
</div>
COMMENTS:
    <div v-if="loadedComments!== '' " >
    <table class="show-facilities-table"> 
    <tr>
        <th>Username</th>
        <th>Comment</th>
        <th>Grade</th>
        <th>Id</th>
        <th>Status</th>
    </tr>
    <tr v-for="c in loadedComments">
        <td>{{c.customerUsername}}</td>
        <td>{{c.text}}</td>
        <td>{{c.grade}}</td>
        <td>{{c.id}}</td>
        <td>{{c.status}}</td>
    </tr>
    </table>
    </div>
    <p v-if="loadedComments==='' "> No available comments!</p>
</div>
</div>		  
`
    ,
    methods:
        {
            login() {
                router.push('/login')
            },
            editProfile() {
                router.push("/edit-profile")
            },
            createOffer() {
                router.push("/open-facility/create-offer")
            },
            getTrainers() {
                router.push("/open-facility/trainers")
            },
            editOffer(id) {
                router.push("/open-facility/offers/" + id)
            },
            getTrainings() {
                router.push("/open-facility/trainings")
            },
            getCustomers() {
                router.push("/open-facility/customers")
            }

        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                    $cookies.set("userInfo", response.data, 10000)
                })
        }

        axios
            .get('/manager/get-facility', this.configHeaders)
            .then(response => {
                this.currentFacility = response.data;
                new ol.Map({
                    target: 'map3',
                    layers: [
                        new ol.layer.Tile({
                            source: new ol.source.OSM()
                        })
                    ],
                    view: new ol.View({
                        center: ol.proj.fromLonLat([this.currentFacility.location.geoLength, this.currentFacility.location.geoWidth]),
                        zoom: 15
                    })

                });
            });
        axios.get('/manager/get-facility-comments',this.configHeaders)
            .then(response=>{
                this.loadedComments=response.data.allComments;
            })
        axios.get('/manager/get-facility-offers', this.configHeaders)
            .then(response => {
                this.offers = response.data.offers;
            });


    },
});