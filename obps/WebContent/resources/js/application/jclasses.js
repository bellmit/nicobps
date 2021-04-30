function CommonMap()
{
    var CommonMap = {
        key: "",
        value: "",
        CommonMap: function (key) {
            this.key=key;
            return CommonMap;	
        },       
        CommonInitMap: function (key,value) {
            this.key=key;
            this.value=value;
            return CommonMap;	
        }        
    };
    return CommonMap;
}
function YESNO()
{
    var YESNO = [
        {key: 'Y', value: 'Yes'},
        {key: 'N', value: 'No'}
    ];
    return YESNO;
}
function Gender()
{
    var Gender = [
        {key: 'M', value: 'Male'},
        {key: 'F', value: 'Female'},
        {key: 'O', value: 'Transgender'}
    ];
    return Gender;
}

