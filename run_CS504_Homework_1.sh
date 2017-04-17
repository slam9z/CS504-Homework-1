#!/usr/bin/env bash
params='?'
if [ $# -eq 0 ]; then
echo "usage: sh run_CS504_Homework_1.sh [options] [arguments]"
echo " "
echo "options:"
echo "-h, --help                show brief help"
echo "-c, --create              upload all data in runningInformationData.json to DB"
echo "-f, --find                find record bt userId"
echo "-d, --delete              delete by runningId"
echo "-p, --purge               delete all records"
echo "-ls, --lists              list all records"
echo ""
echo "sub commands for -ls [options] [arguments]"
echo "--page                     list records filter by page, default is 0"
echo "--size                     list records filter by size per page, default is 2"
echo "--sortBy                   list records filter by params, default is 'heartRate'"
echo "--sortDir                  list records filter by sorting direction, default is 'DESC'"
exit 0
fi

while test $# -gt 0; do
        case "$1" in
                -h|--help)
                        echo "usage: sh run_CS504_Homework_1.sh [options] [arguments]"
                        echo " "
                        echo "options:"
                        echo "-h, --help                show brief help"
                        echo "-c, --create              upload all data in runningInformationData.json to DB"
                        echo "-f, --find                find record by userId"
                        echo "-d, --delete              delete by runningId"
                        echo "-p, --purge               delete all records"
                        echo "-ls, --lists              list all records"
                        echo ""
                        echo "sub commands for -ls [options] [arguments]"
                        echo "--page                     list records filter by page, default is 0"
                        echo "--size                     list records filter by size per page, default is 2"
                        echo "--sortBy                   list records filter by params, default is 'heartRate'"
                        echo "--sortDir                  list records filter by sorting direction, default is 'DESC'"
                        exit 0
                        ;;
                -c|--create)
                        curl -v -H "Content-Type: application/json" localhost:9000/api/runningInformations/add -d @running-informations-data.json | python -m json.tool
                        exit 0
                        ;;

                -f|--find)
                        shift
                        if test $# -gt 0; then
                            curl -v -H "Content-Type: application/json" http://localhost:9000/api/runningInformations/id/$1 | python -m json.tool
                        else
                            echo "sh run_CS504_Homework_1.sh -f <id>"
                        fi
                        exit 0
                        ;;
                -d|--delete)
                        shift
                        if test $# -gt 0; then
                            curl -v -X DELETE http://localhost:9000/api/runningInformations/deleteByRunningId/$1
                        else
                            echo "sh run_CS504_Homework_1.sh -d <runningId>"
                        fi
                        exit 0
                        ;;
                -p|--purge)
                        curl -v -X DELETE localhost:9000/api/runningInformations/purge
                        exit 0
                        ;;
                -ls|--lists)
                        shift
                        echo $1
                        while test $# -gt 0; do
                            case "$1" in
                                --page)
                                    shift
                                    if test $# -gt 0; then
                                       params="${params}page=$1&"
                                    fi
                                    shift
                                    ;;
                                --size)
                                    shift
                                    if test $# -gt 0; then
                                       params="${params}size=$1&"
                                    fi
                                    shift
                                    ;;
                                --sortDir)
                                    shift
                                    if test $# -gt 0; then
                                       params="${params}sortDir=$1&"
                                    fi
                                    shift
                                    ;;
                                --sortBy)
                                    shift
                                    if test $# -gt 0; then
                                       params="${params}sortBy=$1&"
                                    fi
                                    shift
                                    ;;
                            esac
                        done
                        #echo ${params%?}
                        curl -v -H "Accept:application/json" localhost:9000/api/runningInformations/listedBy${params%?} | python -m json.tool

                        ;;
                *)
                        break
                        ;;
        esac
done