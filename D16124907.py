
import pandas as pd
df = pd.read_csv('volcanoes.csv', sep = ',',
                 delimiter = None,encoding='latin-1')
volcano = df[['Name', 'Location','Country','Latitude','Longitude','Elevation','Type','Status']]\
.drop_duplicates()\
.sort_values(['Name','Location','Country','Latitude','Longitude','Elevation','Type','Status'], ascending = [True,True,True,True,True,True,True,True])

def writeafile(filename):
    file = open(filename,'w')
    print('Opening ', filename)
    rec = 'use volcannoActivities\n'
    file.write(rec)
    for r, s,  in thisfile[['Name','Location']].itertuples(index=False):
        tc = (df[(df['Name']==r) & (df['Location']==s)])
        j = (tc.groupby(['Name','Location','Country','Latitude','Longitude','Elevation','Type','Status'], as_index=False)
        .apply(lambda x: x[['Year','Month','Day','Associated Tsunami','Associated Earthquake','Volcano Explosivity Index (VEI)','Agent','DEATHS','DEATHS_DESCRIPTION',
        'MISSING','MISSING_DESCRIPTION','INJURIES','INJURIES_DESCRIPTION','DAMAGE_MILLIONS_DOLLARS','DAMAGE_DESCRIPTION','HOUSES_DESTROYED','HOUSES_DESTROYED_DESCRIPTION',
        'TOTAL_DEATHS','TOTAL_DEATHS_DESCRIPTION','TOTAL_MISSING','TOTAL_MISSING_DESCRIPTION','TOTAL_INJURIES','TOTAL_INJURIES_DESCRIPTION','TOTAL_DAMAGE_MILLIONS_DOLLARS',
        'TOTAL_DAMAGE_DESCRIPTION','TOTAL_HOUSES_DESTROYED','TOTAL_HOUSES_DESTROYED_DESCRIPTION']]
               .to_dict('r'))
             .reset_index()
             .rename(columns={0:'Activities'})
             .to_json(orient='records'))
        rec = 'db.D16124907.insert(' + j + ')\n'
        file.write(rec)
    file.close()
    print('Closing ', filename)
    return()

count = 1
countmax = round(len(volcano)+.5)/100
print(countmax)
filename = 'D16124907.js'
print(filename,'start:',0,' end: ', 99)
thisfile = volcano[0: 99]
print(thisfile.head())
print ('The count is:', 0)
b = writeafile(filename)
while (count <= countmax):
    filename = 'D16124907' + str(count) + '.js'
    print(filename,'start:',count*100-1,' end: ', count*100 + 99)
    thisfile = volcano[count*100 -1: count*100 + 99]
    print(thisfile.head())
    print ('The count is:', count)
    b = writeafile(filename)
    count = count + 1

print ("Finished - GoodBye!")
