kor=["솨",'ㅇ',"ㄹ"]
eng=["adsf",'dd',"gg"]
print(list(zip(kor,eng)))

mixed=[('솨', 'adsf'), ('ㅇ', 'dd'), ('ㄹ', 'gg')]
print(list(zip(*mixed)))

kor2, eng2 = zip(*mixed)
print(kor2)