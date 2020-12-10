#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <regex>
#include <map>

class passport {
    std::map<std::string, std::string> fields;
    
    public:
    passport(std::vector<std::string> parts) {
        try {
            const std::regex re(R"((byr|iyr|eyr|hgt|hcl|ecl|pid|cid0):([^:]+))"); 
            for (auto p : parts) {
                std::smatch match;
                if( std::regex_match( p, match, re ) ) {
                    //std::cout << match[1] << " - " << match[2] << std::endl;;
                    fields[match[1]] = match[2];
                }
            }
        } catch (std::regex_error& e) {
            std::cerr << "Regex error: " << e.code() << std::endl;
        }
    }
    
    bool valid() {
        for(auto e : {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"}) {
            if(fields[e] == "") {
                //std::cout << "  Missing: " << e << " " << fields[e] << std::endl;
                return false;
            }
        }
        return true;
    }
};

int main() {
    //File should have trailnig newline!
    std::ifstream input("day4_input.txt");
    std::string line;
    uint valid = 0;
    passport *p = nullptr;
    std::vector<std::string> parts;
    while (std::getline(input, line))
    {
        if(line.empty()) {
            if(p == nullptr)
                delete p;
            p = new passport(parts);
            if(p->valid())
                valid++;
            parts.clear();
            continue;
        }
        std::istringstream iss(line);        
        std::string part;
        while ((iss >> part)) { 
            parts.emplace_back(part);
        }
    }
    std::cout << "Valid: " << valid << std::endl;
    if(p == nullptr)
        delete p;
    return 0;
}
