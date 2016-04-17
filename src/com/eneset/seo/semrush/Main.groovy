import static com.xlson.groovycsv.CsvParser.parseCsv

static void main(String[] args) {
    def masterCsv = 'etc/teepublic.com-domain_organic-us.csv'
    def slaveCsvs = [
//            'etc/designbyhumans.com-domain_organic-us.csv',
//            'etc/neatoshop.com-domain_organic-us.csv',
//            'etc/redbubble.com-domain_organic-us.csv',
            'etc/society6.com-domain_organic-us.csv',
//            'etc/80stees.com-domain_organic-us.csv',
    ]

    def out = new File('/Users/egor/Desktop/out.txt')

    def master = pullKeywords(masterCsv)
    def slaves = []

    for (slaveCsv in slaveCsvs) {
        slaves.add(pullKeywords(slaveCsv))
    }

    def missing = [:]

    processSlave(master, slaves[0], missing)

    println "Keyword,Position,Search Volume,Url"
    out << "Keyword,Position,Search Volume,Url\n"

    for (kw in missing.keySet()) {
        if (Integer.parseInt(missing[kw].Position) < 20) {
            println missing[kw].Keyword + ',' + missing[kw].Position + ',' + missing[kw]."Search Volume" + ',' + missing[kw].Url
            out << missing[kw].Keyword + ',' + missing[kw].Position + ',' + missing[kw]."Search Volume" + ',' + missing[kw].Url + "\n"
        }
    }
}

static processSlave(master, slave, missing) {
    for (keyword in slave.keySet()) {
        if (!master.containsKey(keyword)) {
            missing[keyword] = slave[keyword]
        }
    }
}

static pullKeywords(String file) {
    def data = loadCsv(file)

    def keywords = [:]

    for (line in data) {
        keywords["$line.Keyword"] = line
    }

    println "Loaded $file"

    return keywords
}

static loadCsv(String file) {
    return parseCsv(new File(file).text)
}