function  build_duri(groupid, type,callback) {
    var _duri = new pushHandle.getSocketItem(groupid, type, callback);
    return _duri;
}