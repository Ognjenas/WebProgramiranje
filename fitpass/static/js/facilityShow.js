Vue.component("facility-show", {
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
            offers: "",
            reserveOffer: {date: {}, time: "", id: ""},
            dateValue: "",
            canWriteComment:false,
            loadedComments:"",
            comment:{
                text:"",
                grade:"",
                username:"",
                facId:"",
            },
            CommentSubmitted:false,
            checkCommentValid:true,
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
    
    <h1>SHOW FACILITY</h1>
    <input type="hidden" id="geoLen" v-model="currentFacility.location.geoLength" onload="makeMap()">
    <input type="hidden" id="geoWidth" v-model="currentFacility.location.geoWidth">
    <table>
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
            <div id="map2" class="map"></div>
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
        
    </table>
    
    
    <h1>Offers</h1>
   
    <div v-for="offer in offers">
        <p>Name: {{offer.name}}</p>
        <p>Type: {{offer.type}}</p>
        <p>Duration: {{offer.duration}}</p>
        <p>Image: <img :src="offer.imgSource" width="100" height="100"></p>
        <button v-if="userInfo.role == 'CUSTOMER'" v-on:click="reserveButton(offer.id)">Reserve</button>
    </div>
    
    COMMENTS:
    <div v-if="loadedComments!== '' " >
    <table>
    <tr>
        <th>Username</th>
        <th>Comment</th>
        <th>Grade</th>
    </tr>
    <tr v-for="c in loadedComments">
        <td>{{c.customerUsername}}</td>
        <td>{{c.text}}</td>
        <td>{{c.grade}}</td>
    </tr>
    </table>
    </div>
    <p v-if="loadedComments==='' "> No available comments!</p>

    
    <div v-if="canWriteComment">
    Write Comment:
    (Please Input both fields to submit comment)
    <input type="text" v-model="comment.text" v-on:input="checkComment">
    Give grade:
    <select name="type" v-model="comment.grade" v-on:change="checkComment">
      <option value="">Select Grade</option>
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <option value="4">4</option>
      <option value="5">5</option>
      <option value="6">6</option>
      <option value="7">7</option>
      <option value="8">8</option>
      <option value="9">9</option>
      <option value="10">10</option>
      </select>
      <button :disabled="checkCommentValid"  v-on:click="createComment">Submit Comment</button>
    </div>
    <p v-if="CommentSubmitted"><b>Comment sent for review by admin!</b></p>
    <button v-on:click="log">LOG</button>
</div>		  
`
    ,
    methods:
        {
            log(){
              console.log(this.loadedComments);
            },
            checkComment(){
              if(this.comment.text=="" || this.comment.grade==""){
                  this.checkCommentValid=true;
              }else{
                  this.checkCommentValid=false;
              }
            },

            createComment(){
                this.comment.username=this.userInfo.username;
                this.comment.facId=this.id;
                axios.post('customer/create-comment',this.comment,this.configHeaders);
                this.CommentSubmitted=true;
                this.canWriteComment=false;
            },
            login() {
                router.push('/login')
            },
            editProfile() {
                router.push("/edit-profile")
            },
            reserveButton(id) {
                router.push("/facility-show/offer/" + id);
            }
        },

    mounted() {
        this.id=this.$route.params.id;
        axios
            .get('/show-facility?id=' + this.$route.params.id)
            .then(response => {
                this.currentFacility = response.data;
                const map = new ol.Map({
                    target: 'map2',
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
                var markers = new ol.layer.Vector({
                    source: new ol.source.Vector(),
                    style: new ol.style.Style({
                        image: new ol.style.Icon({
                            anchor: [0.5, 1],
                            src: 'https://www.clipartmax.com/png/small/103-1033671_store-locator-shoprite-google-location-icon-png.png'
                        })
                    })
                });
                var iconStyle = new ol.style.Style({
                    image: new ol.style.Icon(({
                        scale: 10,
                        offset: [-20, -20],
                        //anchor: [0.5, 1],
                        src: "https://www.clipartmax.com/png/small/103-1033671_store-locator-shoprite-google-location-icon-png.png",
                        opacity: 1,
                        id: num + "_" + x
                    }))
                });
                markers.setStyle(iconStyle);

                map.addLayer(markers);

                var marker = new ol.Feature(new ol.geom.Point(ol.proj.fromLonLat([this.currentFacility.location.geoLength, this.currentFacility.location.geoWidth])));
                markers.getSource().addFeature(marker);
            });

        if ($cookies.get("token") != null) {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                    axios.get('/show-facility/can-comment?id='+this.$route.params.id+'&username='+this.userInfo.username,this.configHeaders)
                        .then(response => {
                            this.canWriteComment=response.data;
                        })
                })
        }


        axios.get('/show-facility/get-confirmed-comments?id='+this.id, this.configHeaders)
            .then(response=>{
                this.loadedComments=response.data.allComments;
                console.log("Dobavlja komentare");
            })

        axios
            .get('/show-facility/offers?id=' + this.$route.params.id, this.configHeaders)
            .then(response => {
                this.offers = response.data.offers;
            })

    },
});