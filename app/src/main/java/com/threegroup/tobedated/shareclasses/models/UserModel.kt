package com.threegroup.tobedated.shareclasses.models

data class UserModel(
    var name            : String = "",
    var birthday        : String = "",
    var pronoun         : String = "",
    var gender          : String = "",
    var height          : String = "",
    var ethnicity       : String = "",
    var star            : String = "",
    var sexOrientation  :String = "",
    var seeking         : String = "",
    var sex             : String = "",
    var testResultsMbti : String = "Not Taken",
    var testResultTbd   : Int = 0,
    var children        : String = "",
    var family          : String = "",
    var education       : String = "",
    var religion        : String = "",
    var politics        : String = "",
    var relationship    : String = "",
    var intentions      : String = "",
    var drink           : String = "",
    var smoke           : String = "",
    var weed            : String = "",
    var meetUp          : String = "",
    var promptQ1        : String = "",
    var promptA1        : String = "",
    var promptQ2        : String = "",
    var promptA2        : String = "",
    var promptQ3        : String = "",
    var promptA3        : String = "",
    var bio             : String = "",
    var image1          : String = "",
    var image2          : String = "",
    var image3          : String = "",
    var image4          : String = "",
    var location        : String = "",
    var status          : Long = 0,
    var number          :String = "",
    var verified        :Boolean = false,
    var userPref        : UserSearchPreferenceModel = UserSearchPreferenceModel(),
    //val liked: List<String>,
    //val passed: List<String>,
)
data class UserSearchPreferenceModel(
    var ageRange            : AgeRange = AgeRange(18, 35),
    var maxDistance         : Int = 25,
    var gender              : List<String> = listOf("Doesn't Matter"),
    var zodiac              : List<String> = listOf("Doesn't Matter"),
    var sexualOri           : List<String> = listOf("Doesn't Matter"),
    var mbti                : List<String> = listOf("Doesn't Matter"),
    var children            : List<String> = listOf("Doesn't Matter"),
    var familyPlans         : List<String> = listOf("Doesn't Matter"),
    var education           : List<String> = listOf("Doesn't Matter"),
    var religion            : List<String> = listOf("Doesn't Matter"),
    var politicalViews      : List<String> = listOf("Doesn't Matter"),
    var relationshipType    : List<String> = listOf("Doesn't Matter"),
    var intentions          : List<String> = listOf("Doesn't Matter"),
    var drink               : List<String> = listOf("Doesn't Matter"),
    var smoke               : List<String> = listOf("Doesn't Matter"),
    var weed                : List<String> = listOf("Doesn't Matter"),
    var meetUp                : List<String> = listOf("Doesn't Matter"),
)

data class PreferenceIndexModel(
    var pronoun : Int = -1,
    var gender : Int = -1,
    var hieght : Int = -1,
    var ethnicity : Int = -1,
    var star: Int = -1,
    var sexOrientation :Int = -1,
    var seeking : Int = -1,
    var sex : Int = -1,
    var children : Int = -1,
    var family : Int = -1,
    var education : Int = -1,
    var religion : Int = -1,
    var politics : Int = -1,
    var relationship : Int = -1,
    var intentions : Int = -1,
    var drink : Int = -1,
    var smoke : Int = -1,
    var weed : Int = -1,
    var promptQ1 : Int = -1,
    var promptA1 : Int = -1,
    var promptQ2 : Int = -1,
    var promptA2 : Int = -1,
    var promptQ3 : Int = -1,
    var promptA3 : Int = -1,
    var meetUp  : Int = -1
)

