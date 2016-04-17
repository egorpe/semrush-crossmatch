import static com.xlson.groovycsv.CsvParser.parseCsv

static void main(String[] args) {
    def csv = '''Name,Lastname
Mark,Andersson
Pete,Hansen'''

    def data = parseCsv(csv)
    for(line in data) {
        println "$line.Name $line.Lastname"
    }
}